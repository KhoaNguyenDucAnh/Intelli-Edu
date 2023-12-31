import React from 'react'
import Schedule from './schedule';
import SidebarContent from './sidebarcontent';
import MindmapContent from './MindmapContent';
import SubjectSelect from './SubjectSelect';
import { useState } from 'react';
import { Select } from '@mantine/core';
import ReactPaginate from 'react-paginate';
import '@mantine/core/styles.css'
function Mindmap(props) {
    const [type, setType] = useState('Sơ đồ tư duy');
    let followingCount = 12
    let CurrentObjects = 12
    const [Selected, setSelected] = useState(0);
    return (
        <>
            <head>
                <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'></link>
                <link href='https://fonts.googleapis.com/css?family=Dongle' rel='stylesheet'></link>
            </head>
            <div className='Frame35MindMap'>
                <div className='Frame20NewMindMap'>
                    <span className='searchBarBackground1'>
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M10 17C13.866 17 17 13.866 17 10C17 6.13401 13.866 3 10 3C6.13401 3 3 6.13401 3 10C3 13.866 6.13401 17 10 17Z" stroke="#272937" stroke-opacity="0.7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                            <path d="M21 21L15 15" stroke="#272937" stroke-opacity="0.7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                        </svg>
                        <input
                            className="searchBar1"
                            placeholder="Tìm kiếm"
                        />
                    </span>
                </div>
                <div className='ContentsMindmap'>
                    <div className='LeftSidebar'>
                        <SidebarContent following={followingCount} isLoggedIn={props.isLoggedIn} />
                        <Select
                            mt={30}
                            defaultValue={"Sơ đồ tư duy"}
                            value={type}
                            onChange={setType}
                            ml={30}
                            size={"lg"}
                            allowDeselect={false}
                            placeholder="Chọn loại tài liệu"
                            checkIconPosition="right"
                            data={['Sơ đồ tư duy', 'Văn bản', 'Câu hỏi']}
                        />
                        <SubjectSelect />
                        <Schedule isLoggedIn={props.isLoggedIn} />
                    </div>
                    <MindmapContent value={type} pageSelected={Selected} CurrentObjects={CurrentObjects} />
                </div>
                <ReactPaginate
                    // breakLabel="..."
                    nextLabel="→"
                    pageRangeDisplayed={5}
                    marginPagesDisplayed={0}
                    pageCount={Math.ceil(CurrentObjects / 9)}
                    previousLabel="←"
                    pageClassName='page-item'
                    pageLinkClassName='page-link'
                    previousClassName='page-item'
                    previousLinkClassName='jpage-link'
                    nextClassName='page-item'
                    nextLinkClassName='page-link'
                    breakClassName='page-item'
                    breakLinkClassName='page-link'
                    containerClassName='pagination'
                    activeClassName='active'
                    onClick={(clickEvent) => {
                        setSelected(clickEvent.nextSelectedPage)
                    }}
                />
            </div>
        </>

    );
}

export default Mindmap;