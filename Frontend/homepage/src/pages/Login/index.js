import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Login() {
    const navigate = useNavigate();

    const [wrongLoginInfo, setWrongLoginInfo] = useState(false)

    const [loginFormData, setLoginFormData] = useState({
        email: '', 
        password: '',
    });

    const [validationErrors, setValidationErrors] = useState({
        email: false,
        password: false
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setLoginFormData({
            ...loginFormData,
            [name]: value,
        });
    }

    const validateForm = () => {
        let isValid = true;

        const errors = {
            email: false,
            password: false
        };

        if (!loginFormData.email.trim()) {
            isValid = false;
            errors.email = true
        }

        if (!loginFormData.password.trim()) {
            isValid = false;
            errors.password = true
        }

        setValidationErrors(errors);
        return isValid;
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        if (validateForm()) {
            // Access form data from the loginFormData object
            const { email, password } = loginFormData;

            // Fetch function to send data to the backend
            fetch('http://localhost:8080/api/v1/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
            })
                .then(response => {
                    if (response.ok) {
                        navigate("/classbook");
                        setWrongLoginInfo(false);
                        return response.json();
                    } else {
                        setWrongLoginInfo(true);
                    }
                })
                .catch(error => {
                    console.error('Login error:', error);
                });

            // Reset the form if needed
            setLoginFormData({ email: '', password: '' });
        }
    }

    return (
        <div className="loginPage">
            <div className="loginPageDecorations">
                <img className="loginPageDecoration1" src={require("../../images/loginPageDecoration1.png")} alt="" />
                <img className="loginPageDecoration2" src={require("../../images/loginPageDecoration2.png")} alt="" />
                <img className="loginPageDecoration3" src={require("../../images/loginPageDecoration3.png")} alt="" />
                <div className="loginPageDecoration4" />
            </div>
            <div className="loginPageContent">
                <p className="loginPageHeader">Chào mừng quay trở lại!</p>
                <form className="loginForm" onSubmit={handleSubmit}>
                    <input
                        className="loginInput"
                        type="text"
                        id="email"
                        name="email"
                        value={loginFormData.email}
                        onChange={handleInputChange}
                        autocomplete="off"
                        placeholder="Email"
                    />
                    {validationErrors.email && <p className="loginFormValidationMessage">Email không được trống</p>}
                    <input
                        className="loginInput"
                        type="password"
                        id="password"
                        name="password"
                        value={loginFormData.password}
                        onChange={handleInputChange}
                        placeholder="Mật khẩu"
                    />
                    {validationErrors.password && <p className="loginFormValidationMessage">Mật khẩu không được trống</p>}
                    {wrongLoginInfo && <p className="loginErrorMessage">Thông tin đăng nhập không chính xác.</p>}
                    <button className="loginSubmitButton" type="submit">Đăng nhập</button>
                </form>
                <span className="loginPageRegisterText">
                    Bạn chưa có tài khoản?
                    <Link to="/register" className="loginPageRegisterButton">Đăng kí</Link>
                </span>
            </div>
        </div>
    )
}

export default Login
