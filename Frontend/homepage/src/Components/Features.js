import Comeherebutton from './Comeherebutton'

function Features() {
    return(
        <div className="features">
            <img alt="" className="featureDescription" src={require('../images/featuredescription.png')}/>
            <div className='feature'>
                <div className='featureContent'>
                    <div className='featureContentHeader'>
                        <div className='line'/>
                        <p className='featureContentHeaderText'>TÓM TẮT BÀI HỌC THEO SỞ THÍCH CỦA BẠN</p>
                    </div>
                    <img alt="" className='featureName' src={require('../images/featurename1.png')}/>
                    <p className='featureContentText'> Sử dụng trí tuệ nhân tạo và bộ câu hỏi được cá nhân hóa để kiểm tra và tổng ôn bài học</p>
                    <Comeherebutton />
                </div>
                <img alt="" className='featurePicture' src={require('../images/feaaturepicture1.png')}/>
            </div>
            <div className='feature'>
                <img alt="" className='featurePicture' src={require('../images/feaaturepicture2.png')}/>
                <div className='featureContent'>
                    <div className='featureContentHeader'>
                        <div className='line'/>
                        <p className='featureContentHeaderText'>LÊN KẾ HOẠCH RÕ RÀNG</p>
                    </div>
                    <img alt="" className='featureName' src={require('../images/featurename2.png')}/>
                    <p className='featureContentText'>Bạn thường hay quên deadline và cảm thấy buồn chán cũng như tự trách vì điều này? Hãy để IntelliEdu giải quyết vấn đề này</p>
                    <Comeherebutton />
                </div>
            </div>
            <div className='feature'>
                <div className='featureContent'>
                    <div className='featureContentHeader'>
                        <div className='line'/>
                        <p className='featureContentHeaderText'>GIẢI TRÍ SAU NHỮNG GIỜ HỌC</p>
                    </div>
                    <img alt="" className='featureName' src={require('../images/featurename3.png')}/>
                    <p className='featureContentText'>Vừa học vừa chơi với một trò chơi giáo dục dựa trên Mario sẽ mang đến cho bạn trải nghiệm phiêu lưu đầy hấp dẫn</p>
                    <Comeherebutton />
                </div>
                <img alt="" className='featurePicture' src={require('../images/feaaturepicture3.png')}/>
            </div>
        </div>
    )
}

export default Features