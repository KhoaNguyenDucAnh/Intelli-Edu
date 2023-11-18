import React from 'react'
class ObjectMindmap extends React.Component {
    render() {
        if (this.props.static >= this.props.max) {
            return (
                <>
                    <div className='MindmapBox'>
                        <div className='MindmapObject'>
                            <img className='MindmapData' src={require("../images/mindmap" + (this.props.Selected + 1) + ".jpg")} alt='Chưa có thông tin'></img>
                            <div className='MindmapTitle'>Mindmap {this.props.Selected + 1}</div>

                        </div>
                    </div>
                </>
            );
        }
    }
}

export default ObjectMindmap;