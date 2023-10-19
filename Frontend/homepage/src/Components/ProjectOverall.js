
function ProjectOverall() {
    return(
        <div className='projectoverall'>
            <img className='projectoverallUpperBackground' src={require('../images/projectoverallupperbackground.png')}/>
            <div className='projectoverallTop'>
                <img className='projectoverallPicture' src={require('../images/projectoverall.png')}/>
                <div>
                    <img className='projectoverallHeader' src={require('../images/projectoverallheader.png')}/>
                    <p className='projectoverallText'>Dự án này nhằm xây dựng một nền tảng học tập đa năng, linh hoạt và hiệu quả, giúp mọi người phát triển và trở thành phiên bản tốt hơn của chính họ. </p>
                </div>
            </div>
        </div>
    )
}

export default ProjectOverall