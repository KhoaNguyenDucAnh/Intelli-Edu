import React from 'react'
import { Link } from "react-router-dom"
import DocType from './DocType';
class mindmap extends React.Component {

    render() {
        return (
            <>
                <head>
                    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'></link>
                    <link href='https://fonts.googleapis.com/css?family=Dongle' rel='stylesheet'></link>
                </head>
                <div className='Frame35MindMap'>
                    <div className='Frame20NewMindMap'>
                        <span className='searchBarBackground'>
                            <svg xmlns="http://www.w3.org/2000/svg" width="1.25%" height="1.25%" viewBox="0 0 24 24" fill="none">
                                <path d="M10 17C13.866 17 17 13.866 17 10C17 6.13401 13.866 3 10 3C6.13401 3 3 6.13401 3 10C3 13.866 6.13401 17 10 17Z" stroke="#272937" stroke-opacity="0.7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                                <path d="M21 21L15 15" stroke="#272937" stroke-opacity="0.7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                            </svg>
                            <input
                                className="searchBar"
                                placeholder="Tìm kiếm"
                            />
                        </span>
                    </div>
                    <div className='ContentsMindmap'>
                        <div className='LeftSidebar'>



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




                            <div className='Schedule'>
                                <div className='ScheduleTitle'>Lịch trình sắp tới</div>

                                <div className='ScheduleInformation'>Khi bạn thực hiện hành động trên IntelliEdu, chúng tôi sẽ cung cấp thông tin tóm tắt tại đây</div>
                            </div>

                            <DocType />




                        </div>







                        <div className='MindmapContent'>
                            <div className='mainContents'>

                            </div>
                            <div className='mainContents'>

                            </div>
                            <div className='mainContents'>

                            </div>
                        </div>
                    </div>
                    <div className='Frame15'>
                        <div className='Frame40'>
                            <div className='Frame40Text'>←
                            </div>
                        </div>
                        <div className='Pagenumber'><div className='PagenumberText'>1
                        </div></div>
                        <div className='Pagenumber'><div className='PagenumberText'>2
                        </div></div>
                        <div className='Pagenumber'><div className='PagenumberText'>3
                        </div></div>
                        <div className='More'>...</div>
                        <div className='Pagenumber'><div className='PagenumberText'>27
                        </div></div>
                        <div className='Pagenumber'><div className='PagenumberText'>28
                        </div></div>
                        <div className='Pagenumber'><div className='PagenumberText'>29
                        </div></div>
                        <div className='Pagenumber'><div className='Frame40Text'>→
                        </div>
                        </div>
                    </div>
                </div>
            </>

        );
    }
}

export default mindmap;