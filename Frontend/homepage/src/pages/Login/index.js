import { useState } from "react";
import { Link } from "react-router-dom";

function Login() {
    const [loginFormData, setLoginFormData] = useState({
        username: '',
        password: '',
    });

    const [validationErrors, setValidationErrors] = useState({
        username: false,
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
            username: false,
            password: false
        };

        if (!loginFormData.username.trim()) {
            isValid = false;
            errors.username = true
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
            const { username, password } = loginFormData;

            // Reset the form if needed
            setLoginFormData({ username: '', password: '' });
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
                        id="username"
                        name="username"
                        value={loginFormData.username}
                        onChange={handleInputChange}
                        autocomplete="off"
                        placeholder="Tên người dùng"
                    />
                    {validationErrors.username && <p className="loginFormValidationMessage">Tên người dùng không được trống</p>}
                    <input
                        className="loginInput"
                        type="password"
                        id="password"
                        name="password"
                        value={loginFormData.password}
<<<<<<< HEAD
                        onChange={handleInputChange} />
                    <Link to="/classbook"><button className="loginSubmitButton" type="submit">Đăng nhập</button></Link>

=======
                        onChange={handleInputChange}
                        placeholder="Mật khẩu"
                    />
                    {validationErrors.password && <p className="loginFormValidationMessage">Mật khẩu không được trống</p>}
                    <button className="loginSubmitButton" type="submit">Đăng nhập</button>
>>>>>>> e39eab93f3c28b0d0643517242827a02216804de
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
