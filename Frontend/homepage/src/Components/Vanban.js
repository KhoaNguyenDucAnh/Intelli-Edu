import React from 'react'
import ObjectText from './ObjectText';
class Vanban extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <ObjectText static={1} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={2} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={3} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>

                <div className='MindmapBox'>
                    <ObjectText static={4} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={5} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={6} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>

                <div className='MindmapBox'>
                    <ObjectText static={7} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={8} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectText static={9} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>


            </>
        );
    }
}

export default Vanban;