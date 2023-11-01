import React from 'react';
import { Routes, Route } from "react-router-dom";
import Cauhoi from './Cauhoi';
export class MindmapContent extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapContent'>
                    <Routes>
                        <Route path="/" element={<Cauhoi />} />
                        <Route path="/mindmap" element={"hello"} />
                        <Route path="/text" element={"Van Ban"} />
                        <Route path="/question" element={<Cauhoi />} />
                    </Routes>
                </div>
            </>
        );
    }
}


export default MindmapContent; 