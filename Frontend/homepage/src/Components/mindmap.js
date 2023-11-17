import React from 'react'
import DocType from './DocType';
import Schedule from './schedule';
import SidebarContent from './sidebarcontent';
import MindmapContent from './MindmapContent';
import Pagenumber from './PageNumber';
import SubjectSelect from './SubjectSelect';
import { useState } from 'react';
import { Select } from '@mantine/core';
import '@mantine/core/styles.css'
class mindmap extends React.Component {
    render() {
        let Type = ''
        const TypeChange = ({ target }) => {
            Type = target;
        }
        return (
            <>
                <head>
                    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'></link>
                    <link href='https://fonts.googleapis.com/css?family=Dongle' rel='stylesheet'></link>
                </head>
                <div className='Frame35MindMap'>
                    <div className='Frame20NewMindMap'>
                        <span className='searchBarBackground'>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
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
                            <SidebarContent />
                            <DocType />

                            <Select
                                onChange={TypeChange}
                                ml={30}
                                size={"lg"}
                                placeholder="Chọn loại tài liệu"
                                checkIconPosition="right"
                                data={['Sơ đồ tư duy', 'Văn bản', 'Câu hỏi']}
                            />
                            <h1>{Type}</h1>
                            <Schedule />
                            <SubjectSelect />
                        </div>
                        <MindmapContent props={Type} />
                    </div>
                    <Pagenumber />
                </div>
            </>

        );
    }
}

export default mindmap;