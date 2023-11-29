import { useState } from "react";
import { Link } from "react-router-dom";

function Register() {

    const [registerFormData, setRegisterFormData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
    });

    const [validationErrors, setValidationErrors] = useState({
        username: false,
        email: false,
        password: false,
        confirmPassword: false,
        wrongconfirm: false
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setRegisterFormData({
            ...registerFormData,
            [name]: value,
        });
    }

    const validateForm = () => {
        let isValid = true;

        const errors = {
            username: false,
            email: false,
            password: false,
            confirmPassword: false,
            wrongconfirm: false
        };

        if (!registerFormData.username.trim()) {
            isValid = false;
            errors.username = true
        }

        if (!registerFormData.email.trim()) {
            isValid = false;
            errors.email = true
        }

        if (!registerFormData.password.trim()) {
            isValid = false;
            errors.password = true
        }

        if (!registerFormData.confirmPassword.trim()) {
            isValid = false;
            errors.confirmPassword = true
        }

        if (registerFormData.password !== registerFormData.confirmPassword && registerFormData.confirmPassword.trim()) {
            isValid = false
            errors.wrongconfirm = true
        }

        setValidationErrors(errors);
        return isValid;
    }

    const handleSubmit = (event) => {
        event.preventDefault()

        if (validateForm()) {
            // Access form data from the registerFormData object
            const { username, email, password, confirmPassword } = registerFormData;

            // Fetch function to send data to the backend
            fetch('http://localhost:8080/api/v1/auth/register', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, username, password, confirmPassword }),
              })
                .then(response => {
                    if (response.ok) {
                        console.log('successful');
                        return response.json();
                    } else {
                        throw new Error(`HTTP Error: ${response.status}`);
                    }
                })
                .then((data) => {
                  console.log('Register response:', data);
                })
                .catch((error) => {
                  console.error('Register error:', error);
                });

            // Reset the form if needed
            setRegisterFormData({ username: '', email: '', password: '', confirmPassword: '' });
        }
    }
    return (
        <div className="registerPage">
            <div className="registerPageDecoration">
                <img className="registerPageDecoration1" src={require("../../images/registerPageDecoration1.png")} alt="" />
                <img className="registerPageDecoration2" src={require("../../images/registerPageDecoration2.png")} alt="" />
                <img className="registerPageDecoration3" src={require("../../images/registerPageDecoration3.png")} alt="" />
            </div>
            <div className="registerPageContent">
                <p className="registerPageHeader">Đăng kí tài khoản</p>
                <form className="registerForm" onSubmit={handleSubmit}>
                    <input
                        className="registerInput"
                        type="text"
                        id="username"
                        name="username"
                        value={registerFormData.username}
                        onChange={handleInputChange}
                        autocomplete="off"
                        placeholder="Tên người dùng"
                    />
                    {!validationErrors.username && <div className="validationSpace" />}
                    {validationErrors.username && <p className="registerFormValidationMessage">Tên người dùng không được trống</p>}
                    <input
                        className="registerInput"
                        type="email"
                        id="email"
                        name="email"
                        value={registerFormData.email}
                        onChange={handleInputChange}
                        placeholder="Email"
                    />
                    {!validationErrors.email && <div className="validationSpace" />}
                    {validationErrors.email && <p className="registerFormValidationMessage">Email không được trống</p>}
                    <input
                        className="registerInput"
                        type="password"
                        id="password"
                        name="password"
                        value={registerFormData.password}
                        onChange={handleInputChange}
                        placeholder="Mật khẩu"
                    />
                    {!validationErrors.password && <div className="validationSpace" />}
                    {validationErrors.password && <p className="registerFormValidationMessage">Mật khẩu không được trống</p>}
                    <input
                        className="registerInput"
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        value={registerFormData.confirmPassword}
                        onChange={handleInputChange}
                        placeholder="Xác nhận mật khẩu"
                    />
                    {!(validationErrors.confirmPassword || validationErrors.wrongconfirm) && <div className="validationSpace" />}
                    {validationErrors.confirmPassword && <p className="registerFormValidationMessage">Mật khẩu không được trống</p>}
                    {validationErrors.wrongconfirm && <p className="registerFormValidationMessage">Mật khẩu xác nhận không đúng</p>}
                    {/* <Link to="/classbook"> */}
                    <button className="registerSubmitButton" type="submit">Đăng kí</button>
                    {/* </Link> */}
                </form>
                <span className="registerPageLoginText">
                    Bạn đã có tài khoản?
                    <Link to="/login" className="registerPageLoginButton">Đăng nhập</Link>
                </span>
            </div>
        </div>
    )
}

export default Register