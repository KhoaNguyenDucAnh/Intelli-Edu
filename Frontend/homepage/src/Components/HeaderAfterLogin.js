import { Link } from "react-router-dom"
import AfterLoginAuth from "./AfterLoginAuth";
function HeaderAfterLogin(props) {
    return (
        <div className="header">
            <div className="frameHeader">
                <div className="leftHeader">
                    <p className="logo">IntelliEdu</p>
                    <div className="navigation">
                        <div className="navItem">
                            <Link to="/" className='homePage'><p className="navText">Trang chủ</p></Link>
                        </div>
                        <div className="withArrow navItem">
                            <p className="navText">Công cụ hỗ trợ</p>
                            <span class="material-symbols-outlined downArrow">
                                expand_more
                            </span>
                            <div className="toolListWrapper">
                                <div className="toolList">
                                    <p className="toolListItem">Mô hình ngôn ngữ lớn</p>
                                    <p className="toolListItem">Sơ đồ tư duy</p>
                                </div>
                            </div>
                        </div>
                        <div className="noArrow navItem">
                            <p className="navText">Kế hoạch</p>
                        </div>
                        <div className="noArrow navItem">
                            <p className="navText">Liên hệ</p>
                        </div>
                    </div>
                </div>
                <div className="auth">
                    <AfterLoginAuth isLoggedIn={props.isLoggedIn} />
                </div>
            </div>
        </div>
    )
}

export default HeaderAfterLogin
