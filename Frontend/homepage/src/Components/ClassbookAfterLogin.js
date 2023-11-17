// import Heading from "./heading";
import Footing from './Footing'
import Mindmap from "./mindmap";
import HeaderAfterLogin from "./HeaderAfterLogin"
import React from 'react'

import { MantineProvider, Select } from "@mantine/core";
class Classbook extends React.Component {
    render() {
        return (
            <>
                <MantineProvider >
                    <div className="App">

                        <HeaderAfterLogin />
                        <Mindmap />
                        <Footing />
                    </div>
                </MantineProvider>
            </>
        );
    }
}

export default Classbook;