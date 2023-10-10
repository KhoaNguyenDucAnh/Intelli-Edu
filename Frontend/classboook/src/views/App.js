import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Body from './Body';
import Heading from './heading'
import Footing from './Footing'
import Mindmap from "./mindmap";
import Newchat from "./newchat";
import Chathistory from "./chathistory";
function App() {
    return (
        <Router>
            <div className="App">
                <Heading />
                <Routes>
                    <Route path="/" element={<Mindmap />} />
                    <Route path="/mindmap" element={<Mindmap />} />
                    <Route path="/Newchat" element={<Newchat />} />
                    <Route path="/chathistory" element={<Chathistory />} />
                </Routes>
                <Footing />
            </div>
        </Router>
    );
}

export default App;
