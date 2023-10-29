import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Heading from './heading'
import Footing from './Footing'
import Mindmap from "./mindmap";
function App() {
    return (
        <Router>
            <div className="App">
                <Heading />
                <Routes>
                    <Route path="/" element={<Mindmap />} />
                </Routes>
                <Footing />
            </div>
        </Router>
    );
}

export default App;
