import React from 'react';
import leaderboardicon1 from '../images/leaderboardicon1.png';
import leaderboardicon2 from '../images/leaderboardicon2.png';
import leaderboardicon3 from '../images/leaderboardicon3.png';

function LeaderboardItem({ username, likes, type }) {
    const imageSrc = [leaderboardicon1, leaderboardicon2, leaderboardicon3];
    const styles = [
        {
            top: '17vw',
            left: '7.5vw'
        },
        {
            top: '20vw',
            left: '27.5vw'
        },
        {
            top: '26.5vw',
            left: '7vw'
        },
    ]

    return (
        <div style={styles[type]} className="LeaderboardItem">
            <div className="LeaderboardItemBackground">
                <img className='LeaderboardIcon' src={imageSrc[type]} alt="" />
            </div>
            <div className="LeaderboardItemContent">
                <p className="LeaderboardItemUsername">{username}</p>
                <p className="LeaderboardItemLikes">{likes} lượt thích</p>
            </div>
        </div>
    );
}

export default LeaderboardItem;
