import React from 'react'
class ObjectMindmap extends React.Component {
    render() {
        if ((this.props.static + (9 * this.props.Selected)) <= this.props.max) {
            return (
                <>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require("../images/mindmap1" + ".jpg")} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.static + 9 * this.props.Selected}</div>
                    </div>
                </>
            );
        }
    }
}

export default ObjectMindmap;