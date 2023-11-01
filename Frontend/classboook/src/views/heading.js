import React from 'react'
import { Link } from "react-router-dom"
class Heading extends React.Component {
    state = {
        MindMap: false,
        New: false,
        ChatHistory: true,
        MindMapColor: "#757575",
        NewColor: "#757575",
        ChatHistoryColor: "#757575",
    }
    render() {
        return (
            <>
                <div className="header">
                    <div className="frameHeader">
                        <div className="leftHeader">
                            <p className="logo">IntelliEdu</p>
                            <div className="navigation">

                                <div className="noArrow navItem">
                                    <p className="navText">Trang chủ</p>
                                </div>

                                <div className="noArrow navItem">
                                    <p className="navText">Diễn đàn</p>
                                </div>

                                <div className="withArrow navItem">
                                    <p className="navText">Công cụ hỗ trợ</p>
                                    <svg className='downArrow' xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 14 14" fill="none">
                                        <path d="M3.76456 6.60889L7.22673 10.0713L10.6891 6.60889" stroke="#757575" stroke-width="1.6" />
                                    </svg>
                                    <div className="toolListWrapper">
                                        <div className="toolList">
                                            <p className="toolListItem">Tăng tốc độ đọc →</p>
                                            <p className="toolListItem">Chatbot →</p>
                                            <p className="toolListItem">Sơ đồ tư duy →</p>
                                        </div>
                                    </div>
                                </div>
                                <div className="withArrow navItem">
                                    <p className="navText">Giải trí</p>
                                    <svg className='downArrow' xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 14 14" fill="none">
                                        <path d="M3.76456 6.60889L7.22673 10.0713L10.6891 6.60889" stroke="#757575" stroke-width="1.6" />
                                    </svg>
                                    <div className="toolListWrapper">
                                        <div className="toolList2">
                                            <p className="toolListItem">Tinh linh đại chiến →</p>
                                            <p className="toolListItem">Bảng xếp hạng →</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="auth">
                            <Link to="/register" className="register">Đăng kí →</Link>
                            <Link to="/login" className="login">Đăng Nhập</Link>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default Heading;