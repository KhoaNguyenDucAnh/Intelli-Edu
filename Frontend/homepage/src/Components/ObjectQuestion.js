import React from 'react'
class ObjectQuestion extends React.Component {
    render() {
        if ((this.props.static + (9 * this.props.Selected)) <= this.props.max) {
            return (
                <>
                    <div className='MindmapObject'>
                        Câu hỏi {this.props.static + 9 * this.props.Selected}
                    </div>
                </>
            );
        }
    }
}

export default ObjectQuestion;