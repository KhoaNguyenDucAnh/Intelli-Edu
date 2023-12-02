import LeaderboardItem from './LeaderboardItem'

function Search() {
    return(
        <div className="search">
            <div className="searchContent">
                <img alt="" className='slogan' src={require('../images/AppSlogan.png')}/>
                <div className='searchBarBackground'>
                    <span class="material-symbols-outlined searchIcon">
                        search
                    </span>
                    <input 
                        className="searchBar"
                        placeholder='Nhập từ khoá hoặc đề tài bạn quan tâm... '
                    />  
                </div>
            </div>
            <div className="leaderboard">
                <img alt="" className='trophy' src={require('../images/trophy.png')}/>
                <p className='trophyText1'>BXH</p>
                <p className='trophyText2'>Tháng 11</p>
                <LeaderboardItem username='MU' likes='10000' type='0'/>
                <LeaderboardItem username='MC' likes='8000' type='1'/>
                <LeaderboardItem username='AIA' likes='1' type='2'/>
            </div>
        </div>
    )
}

export default Search