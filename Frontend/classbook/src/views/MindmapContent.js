import React from 'react';
import { Routes, Route } from "react-router-dom";
import Cauhoi from './Cauhoi';
import Sodo from './Sodo';
export class MindmapContent extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapContent'>
                    <Routes>
                        <Route path="/" element={<Cauhoi />} />
                        <Route path="/mindmap" element={<Sodo />} />
                        <Route path="/text" element={"Văn bản"} />
                        <Route path="/question" element={<Cauhoi />} />
                    </Routes>
                </div>
            </>
        );
    }
}


export default MindmapContent; 