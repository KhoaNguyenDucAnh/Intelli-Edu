import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Heading from './heading'
import Header from "./Header";
import Footing from './Footing'
import Mindmap from "./mindmap";
import { MantineProvider, Select } from "@mantine/core";
function App() {
    return (
        <MantineProvider >
            <Router>
                <div className="App">
                    <Header />
                    <Mindmap />
                    <Footing />
                </div>
            </Router>
        </MantineProvider>
    );
}

export default App;
