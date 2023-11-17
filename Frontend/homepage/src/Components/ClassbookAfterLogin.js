// import Heading from "./heading";
import Footing from './Footing'
import Mindmap from "./mindmap";
import HeaderAfterLogin from "./HeaderAfterLogin"
import React from 'react'
import { MantineProvider } from "@mantine/core";
class Classbook extends React.Component {
    render() {
        return (
            <>
                <MantineProvider >
                    <div className="App">

                        <HeaderAfterLogin isLoggedIn={this.props.isLoggedIn} />
                        <Mindmap isLoggedIn={this.props.isLoggedIn} />
                        <Footing />
                    </div>
                </MantineProvider>
            </>
        );
    }
}

export default Classbook;