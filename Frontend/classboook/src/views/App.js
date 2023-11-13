import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Heading from './heading'
import Header from "./Header";
import Footing from './Footing'
import Mindmap from "./mindmap";
function App() {
    return (
        <Router>
            <div className="App">
                <Header />
                {/* <Routes>
                    <Route path="/" element={<Mindmap />} />
                    <Route path="/question" element={<Mindmap />} />
                    <Route path="/mindmap" element={<Mindmap />} />
                    <Route path="/text" element={<Mindmap />} />
                </Routes> */}
                <Mindmap />
                <Footing />
            </div>
        </Router>
    );
}

export default App;
