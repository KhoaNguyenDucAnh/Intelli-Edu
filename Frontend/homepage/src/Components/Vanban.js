import React from 'react'
class Vanban extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 1}
                        <img></img>
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 2}
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 3}
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 4}
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 5}
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 6}
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 7}
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 8}
                    </div>

                    <div className='MindmapObject'>
                        Văn bản {this.props.Selected * 9 + 9}
                    </div>
                </div>


            </>
        );
    }
}

export default Vanban;