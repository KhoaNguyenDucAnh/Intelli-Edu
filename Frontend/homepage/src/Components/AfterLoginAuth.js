import React from 'react'
import { Avatar } from '@mantine/core';
import { Link } from "react-router-dom"

class AfterLoginAuth extends React.Component {
    render() {
        if (this.props.isLoggedIn) {
            return (
                <>
                    <Link to="/biography" className='link'>
                        <div className="noArrow navItem">
                            <p className="navText">Nguyễn Ngọc Đạt</p>
                            <Avatar variant="light" radius="" size="lg" color="rgba(2, 198, 242, 1)" src={require('../images/Dat.png')} />
                        </div>
                    </Link>
                </>
            );
        }
        else {
            return (
                <>
                    <Link to="/login" className="login">Đăng nhập</Link>
                    <Link to="/register" className="register">Đăng kí</Link>
                </>
            )
        }
    }
}

export default AfterLoginAuth;