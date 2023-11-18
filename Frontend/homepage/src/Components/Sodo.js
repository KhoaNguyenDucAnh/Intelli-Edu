import React from 'react';
import ObjectMindmap from './ObjectMindmap';
class Sodo extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <ObjectMindmap
                        static={1}
                        Selected={this.props.Selected}
                        max={this.props.CurrentObjects} />
                    <ObjectMindmap static={2} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectMindmap static={3} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div >

                <div className='MindmapBox'>
                    <ObjectMindmap static={4} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectMindmap static={5} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectMindmap static={6} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>

                <div className='MindmapBox'>
                    <ObjectMindmap static={7} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectMindmap static={8} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectMindmap static={9} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>


            </>
        );
    }
}

export default Sodo;