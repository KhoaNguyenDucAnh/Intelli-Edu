import React from 'react'
import { Select } from '@mantine/core';
import '@mantine/core/styles.css'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Link } from "react-router-dom"
class DocType extends React.Component {
    render() {
        return (
            <>
                {/* <div className='dropdown'>
                    <div className='select'>
                        <span className='selected'>
                            <Routes>
                                <Route path="/" element={"Chọn loại tài liệu"} />
                                <Route path="/mindmap" element={"Sơ đồ tư duy"} />
                                <Route path="/text" element={"Văn bản"} />
                                <Route path="/question" element={"Câu hỏi"} />
                            </Routes>
                        </span>
                        <div className='caret'></div>
                    </div>
                    <ul className='menu'>
                        <li><Link to="/mindmap" className='menu-item'>Sơ đồ tư duy</Link></li>
                        <li><Link to="/text" className='menu-item'>Văn bản</Link></li>
                        <li><Link to="/question" className='menu-item'>Câu hỏi</Link></li>
                    </ul>
                </div> */}
                <Select
                    ml={30}
                    size={"lg"}
                    placeholder="Chọn loại tài liệu"
                    checkIconPosition="right"
                    data={['Sơ đồ tư duy', 'Văn bản', 'Câu hỏi']}
                />

            </>
        );
    }
}

export default DocType;