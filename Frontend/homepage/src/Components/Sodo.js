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
                        Mindmap 3
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        Mindmap 1
                    </div>

                    <div className='MindmapObject'>
                        Mindmap 2
                    </div>

                    <div className='MindmapObject'>
                        Mindmap 3
                    </div>
                </div>

                <div className='MindmapBox'>
                    <div className='MindmapObject'>
                        Mindmap 1
                    </div>

                    <div className='MindmapObject'>
                        Mindmap 2
                    </div>

                    <div className='MindmapObject'>
                        Mindmap 3
                    </div>
                </div>


            </>
        );
    }
}

export default Sodo;