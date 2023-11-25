import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import ClassbookAfterLogin from "./Components/ClassbookAfterLogin"
import Register from "./pages/Register";
import Biography from "./pages/Biography";
// import Classbook from "./Components/Classbook";
function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/search" element={<ClassbookAfterLogin isLoggedIn={false} />} />
          <Route path="/login" element={<Login />} />
          <Route path="/biography" element={<Biography />} />
          <Route path="/register" element={<Register />} />
          <Route path="/classbook" element={<ClassbookAfterLogin isLoggedIn={true} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;