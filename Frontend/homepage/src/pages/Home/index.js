import Header from "../../Components/Header";
import Search from "../../Components/Search";
import Features from "../../Components/Features";
import ProjectOverall from "../../Components/ProjectOverall";
import Footer from "../../Components/Footer";

function Home(){
    return(
        <div>
            <Header />
            <Search />
            <Features />
            <ProjectOverall />
            <Footer />
        </div>
    )
}

export default Home