import React from 'react';
import Cauhoi from './Cauhoi';
import Sodo from './Sodo';
import Vanban from './Vanban';
export class MindmapContent extends React.Component {
    render() {
        if (this.props.value == 'Sơ đồ tư duy') {
            return (
                <div className='MindmapContent'>
                    <Sodo />
                </div>
            )
        }
        if (this.props.value == "Văn bản") {
            return (

                <div className='MindmapContent'>
                    <Vanban />
                </div>
            )
        }
        if (this.props.value == "Câu hỏi") {
            return (
                <div className='MindmapContent'>
                    <Cauhoi />
                </div>
            )
        }
    }
}

export default MindmapContent; 