import React from 'react'
class Schedule extends React.Component {
    render() {
        if (this.props.isLoggedIn) {
            return (
                <>

                    <div className='Schedule'>
                        <div className='ScheduleTitle'>Lịch trình sắp tới</div>

                        <div className='ScheduleInformation'>Khi bạn thực hiện hành động trên IntelliEdu, chúng tôi sẽ cung cấp thông tin tóm tắt tại đây</div>
                    </div>
                </>
            );
        }
    }
}

export default Schedule;