import { useState } from "react";
import { Link } from "react-router-dom";

function Login() {
    const [loginFormData, setLoginFormData] = useState({
        username: '',
        password: '',
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setLoginFormData({
            ...loginFormData,
            [name]: value,
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        // Access form data from the loginFormData object
        const { username, password } = loginFormData;

        // Reset the form if needed
        setLoginFormData({ username: '', password: '' });
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
                    <label className="loginUsernameLabel" htmlFor="username">Tên người dùng:</label>
                    <input
                        className="loginUsernameInput"
                        type="text"
                        id="username"
                        name="username"
                        value={loginFormData.username}
                        onChange={handleInputChange}
                        autocomplete="off"
                    />
                    <label className="loginPasswordLabel" htmlFor="password">Mật khẩu:</label>
                    <input
                        className="loginPasswordInput"
                        type="password"
                        id="password"
                        name="password"
                        value={loginFormData.password}
                        onChange={handleInputChange}
                    />
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
