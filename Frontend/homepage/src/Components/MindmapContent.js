import React from 'react';
import Cauhoi from './Cauhoi';
import Sodo from './Sodo';
import Vanban from './Vanban';
export class MindmapContent extends React.Component {
    render() {
        if (this.props.value == 'Sơ đồ tư duy') {
            return (
                <div className='MindmapContent'>
                    <Sodo Selected={this.props.pageSelected} CurrentObjects={this.props.CurrentObjects} />
                </div>
            )
        }
        if (this.props.value == "Văn bản") {
            return (

                <div className='MindmapContent'>
                    <Vanban Selected={this.props.pageSelected} CurrentObjects={this.props.CurrentObjects} />
                </div>
            )
        }
        if (this.props.value == "Câu hỏi") {
            return (
                <div className='MindmapContent'>
                    <Cauhoi Selected={this.props.pageSelected} CurrentObjects={this.props.CurrentObjects} />
                </div>
            )
        }
    }
}

export default MindmapContent; 