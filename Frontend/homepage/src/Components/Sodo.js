import React from 'react'
class Sodo extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 1</div>

                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap2.png')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 2</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap3.jpeg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 3</div>
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap4.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 4</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 5</div>
                    </div>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 6</div>
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 7</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 8</div>
                    </div>

                    <div className='MindmapObject'>
                        <img className='MindmapData' src={require('../images/mindmap1.jpg')} alt='logo'></img>
                        <div className='MindmapTitle'>Mindmap 9</div>
                    </div>
                </div>


            </>
        );
    }
}

export default Sodo;