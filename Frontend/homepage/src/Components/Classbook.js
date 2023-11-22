import Heading from "./heading";
import Footing from './Footing'
import Mindmap from "./mindmap";
import Header from "./Header"
import React from 'react'
import { MantineProvider, Select } from "@mantine/core";
class Classbook extends React.Component {
    render() {
        return (
            <>
                <MantineProvider >
                    <div className="App">
                        {/* <Heading /> */}
                        <Header />
                        <Mindmap />
                        <Footing />
                    </div>
                </MantineProvider>
            </>
        );
    }
}

export default Classbook;