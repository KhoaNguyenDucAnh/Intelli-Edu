import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import ClassbookAfterLogin from "./Components/ClassbookAfterLogin"
import Register from "./pages/Register";
// import Classbook from "./Components/Classbook";
function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<ClassbookAfterLogin isLoggedIn={false} />} />
          <Route path="/homePage" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/classbook" element={<ClassbookAfterLogin isLoggedIn={true} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;