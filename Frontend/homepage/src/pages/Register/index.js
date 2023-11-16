import { useState } from "react";
import { Link } from "react-router-dom";

function Register() {

    const [registerFormData, setRegisterFormData] = useState({
        fullname: '',
        username: '',
        email: '',
        password: '',
        confirmpassword: ''
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setRegisterFormData({
            ...registerFormData,
            [name]: value,
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        // Access form data from the registerFormData object
        const { fullname, username, email, password } = registerFormData;

        // Reset the form if needed
        setRegisterFormData({ fullname: '', username: '', email: '', password: '', confirmpassword: '' });
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
                    <label className="registerLabel" htmlFor="fullname">Họ tên:</label>
                    <input
                        className="registerInput"
                        type="text"
                        id="fullname"
                        name="fullname"
                        value={registerFormData.fullname}
                        onChange={handleInputChange}
                        autocomplete="off"
                    />
                    <label className="registerLabel" htmlFor="username">Tên người dùng:</label>
                    <input
                        className="registerInput"
                        type="text"
                        id="username"
                        name="username"
                        value={registerFormData.username}
                        onChange={handleInputChange}
                        autocomplete="off"
                    />
                    <label className="registerLabel" htmlFor="email">Email:</label>
                    <input
                        className="registerInput"
                        type="email"
                        id="email"
                        name="email"
                        value={registerFormData.email}
                        onChange={handleInputChange}
                    />
                    <label className="registerLabel" htmlFor="password">Mật khẩu:</label>
                    <input
                        className="registerInput"
                        type="password"
                        id="password"
                        name="password"
                        value={registerFormData.password}
                        onChange={handleInputChange}
                    />
                    <label className="registerLabel" htmlFor="confirmpassword">Xác nhận mật khẩu:</label>
                    <input
                        className="registerInput"
                        type="password"
                        id="confirmpassword"
                        name="confirmpassword"
                        value={registerFormData.confirmpassword}
                        onChange={handleInputChange}
                    />

                    <Link to="/classbook"><button className="registerSubmitButton" type="submit">Đăng kí</button></Link>
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