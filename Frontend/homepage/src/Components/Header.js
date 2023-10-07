import { Link } from "react-router-dom"

function Header() {
    return(
        <div className="header">
            <div className="frameHeader">
                <div className="leftHeader">
                    <p className="logo">IntelliEdu</p>
                    <div className="navigation">
                    <div className="navItem">
                            <p className="navText">Tài liệu</p>
                            <span class="material-symbols-outlined downArrow">
                                expand_more
                            </span>
                        </div> 
                        <div className="withArrow navItem">
                            <p className="navText">Công cụ hỗ trợ</p>
                            <span class="material-symbols-outlined downArrow">
                                expand_more
                            </span>
                            <div className="toolListWrapper">
                                <div className="toolList">
                                    <p className="toolListItem">Tăng tốc độ đọc</p>
                                    <p className="toolListItem">Chatbot</p>
                                    <p className="toolListItem">Sơ đồ tư duy</p>
                                </div>
                            </div>
                        </div> 
                        <div className="noArrow navItem">
                            <p className="navText">Kế hoạch</p>
                        </div>
                        <div className="noArrow navItem">
                            <p className="navText">Giải trí</p>
                        </div>
                        <div className="noArrow navItem">
                            <p className="navText">Liên hệ</p>
                        </div>
                    </div>
                </div>
                <div className="auth">
                    <Link to="/login" className="login">Đăng nhập</Link>
                    <Link to="/register" className="register">Đăng kí</Link>
                </div>
            </div>
        </div>
    )
}

export default Header