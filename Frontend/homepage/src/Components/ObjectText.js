import React from 'react'
class ObjectText extends React.Component {
    render() {
        if ((this.props.static + (9 * this.props.Selected)) <= this.props.max) {
            return (
                <>
                    <div className='MindmapObject'>
                        Văn bản {this.props.static + 9 * this.props.Selected}
                    </div>
                </>
            );
        }
    }
}

export default ObjectText;