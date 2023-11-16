
function Comeherebutton() {
    return (
        <div className="comehereButton">
            <p className="comehereText">Đến ngay →</p>
            <div className='comehereBackground' />
            <div className="comehereText">
                Đến ngay
                <img className="comehereTextArrow" src={require("../images/rightarrow.png")} alt="bruh" />
            </div>
            <div className='comehereBackground' />
        </div >
    )
}

export default Comeherebutton