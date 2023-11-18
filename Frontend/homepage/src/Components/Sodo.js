import React from 'react';
import ObjectMindmap from './ObjectMindmap';
class Sodo extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require("../images/mindmap1" + ".jpg")} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 1}</div>

                    </div>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap2.png')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 2}</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap3.jpeg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 3}</div>
                    </div>
                </div >

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap4.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 4}</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 5}</div>
                    </div>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 6}</div>
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 7}</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 8}</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='Chưa có thông tin'></img>
                        <div className='MindmapTitle'>Mindmap {this.props.Selected * 9 + 9}</div>
                    </div>
                </div>


            </>
        );
    }
}

export default Sodo;