import { Link } from "react-router-dom";

function Comeherebutton() {
    return (
        <Link to="/classbook" className="registerPageLoginButton">
            <div className="comehereButton">
                <div className="comehereText">
                    Đến ngay
                    <img className="comehereTextArrow" src={require("../images/rightarrow.png")} alt="bruh"/>
                </div>
                <div className='comehereBackground'/>
            </div>
        </Link>
    )
}

export default Comeherebutton
