import React from 'react'
class SidebarContent extends React.Component {
    render() {
        return (
            <>

                <div className='SidebarContent'>
                    <div className='ContentBox'>
                        <div className='Content'>
                            <div className='ContentText'>Gần đây</div>
                        </div>
                        <div className='Content'>
                            <div className='ContentText'>Nổi bật</div>
                        </div>
                        <div className='Content'>
                            <div className='ContentText'>Đang theo dõi</div>
                        </div>
                    </div>
                </div>

            </>
        );
    }
}

export default SidebarContent;