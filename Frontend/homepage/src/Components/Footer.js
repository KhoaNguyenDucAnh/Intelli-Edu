import { Link } from 'react-router-dom'

function Footer() {
    return(
        <div className='footer'>
            <img alt="" className="footerUpperBackground" src={require('../images/footerupperbackground.png')}/>
            <img alt="" className='footerHeader' src={require('../images/footerheader.png')}/>
            <div className='footerContent'>
                <div className="footerAuth">
                    <Link to="/login" className="footerLogin">Đăng nhập</Link>
                    <Link to="/register" className="footerRegister">Đăng kí</Link>
                </div>
                <div className='footerStats'>
                    <div className='footerStat'>
                        <p className='footerStatNum'>300+</p>
                        <p className='footerStatText'>Sơ đồ</p>
                    </div>
                    <div className='footerStat'>
                        <p className='footerStatNum'>500+</p>
                        <p className='footerStatText'>Người dùng</p>
                    </div>
                </div>
                <div className='footerComment'>
                    <img alt="" className='commentAccessory' src={require('../images/commentaccessory.png')}/>
                    <div className='stars'>
                        <img alt="" className='star' src={require('../images/star.png')}/>
                        <img alt="" className='star' src={require('../images/star.png')}/>
                        <img alt="" className='star' src={require('../images/star.png')}/>
                        <img alt="" className='star' src={require('../images/star.png')}/>
                        <img alt="" className='star' src={require('../images/star.png')}/>
                    </div>
                    <p className='footerCommentText'>Dự án tuyệt zời vloz</p>
                    <div className='commentUser'>
                        <img alt="" className='commentUserAvatar' src={require('../images/commentuseravatar.png')}/>
                        <div className='commentUserInfo'>
                            <p className='commentUsername'>Kathryn Murphy</p>
                            <p className='commentUserJob'>General Electric</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Footer