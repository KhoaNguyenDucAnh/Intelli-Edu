import React from 'react'
import { Avatar } from '@mantine/core'
import { Link } from "react-router-dom"
import { useState } from 'react';

function AfterLoginAuth(props){
    const [changeUsername, setChangeUsername] = useState(false)
    const [newUsername, setNewUsername] = useState('Nguyễn Ngọc Đạt')
    const [settingOn, setSettingOn] = useState(false)

    const handleInputChange = (event) => {
        setNewUsername(event.target.value);
    };

    function handleClick(){
        setSettingOn(!settingOn)
        setChangeUsername(false)
    }
    
 
    if (props.isLoggedIn) {
        return (
            <>
                <div className="authNavItem">
                    <p className="authNavText">{newUsername}</p>
                    <Avatar variant="light" radius="" size="md" color="rgba(2, 198, 242, 1)" src={require('../images/Dat.png')} />
                    <img onClick={() => handleClick()} className='settingIcon' src={require("../images/settingIcon.png")} alt=""/>
                    {settingOn && <div className='settingPanel'>
                        {!changeUsername && <div className='settingUsernameAva'>
                            <div className='settingUsernameAvaInfo'>
                                <img className='settingUserAva' src={require("../images/Dat.png")}/>
                                <div className='settingUsernameId'>
                                    <p className='settingUsername'>{newUsername}</p>
                                    <p className='settingUserID'>ID: 01593</p>
                                </div>
                            </div>
                            <p className='usernameModifyButton' onClick={() => setChangeUsername(true)}>Chỉnh sửa</p>
                        </div>}
                        {changeUsername && <div className='settingChangeUsername'>
                            <img className='settingUserAva' src={require("../images/Dat.png")}/>
                            <input 
                                className='settingChangeUsernameInput' 
                                type='text'
                                onChange={handleInputChange}
                            />
                            <p className='settingChangeUsernameApply' onClick={() => setChangeUsername(false)}>Áp dụng</p>
                        </div>}
                        <p className='deleteAccountButton'>Xóa tài khoản</p>
                    </div>}
                </div>
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

export default AfterLoginAuth;