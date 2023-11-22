import React from 'react'
import ObjectQuestion from './ObjectQuestion';
class Cauhoi extends React.Component {
    render() {
        return (
            <>
                <div className='MindmapBox'>
                    <ObjectQuestion static={1} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={2} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={3} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>

                <div className='MindmapBox'>
                    <ObjectQuestion static={4} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={5} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={6} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>

                <div className='MindmapBox'>
                    <ObjectQuestion static={7} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={8} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                    <ObjectQuestion static={9} Selected={this.props.Selected} max={this.props.CurrentObjects} />
                </div>
            </>
        );
    }
}

export default Cauhoi;