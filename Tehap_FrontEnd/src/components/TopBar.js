import React from 'react';
import icon from '../assets/icon-light.png';

import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { logoutSucces } from '../redux/authActions';
import ProfileImageWithDefault from './ProfileImageWithDefault';
import { useState } from 'react';
import { useEffect } from 'react';
import { useRef } from 'react';

const TopBar = (props) => {

    const { t } = useTranslation();

    const { username, isLoggedIn, displayName, image } = useSelector((store) => { //reduxState'ten alıyoruz gibi...
        return {
            isLoggedIn: store.isLoggedIn,
            username: store.username,
            displayName: store.displayName,
            image: store.image,
        }
    })


    const menuArea = useRef(null);
    const [menuVisible, setMenuVisible] = useState(false);

    useEffect(() => {
        document.addEventListener('click', menuClickTracker);
        return () => {
            document.removeEventListener('click', menuClickTracker);
        }
    }, [isLoggedIn]);

    const menuClickTracker = (event) => {
        if (menuArea.current === null || !menuArea.current.contains(event.target)) {
            setMenuVisible(false);
        }
    };

    const dispatch = useDispatch();

    const onLogoutSuccess = () => {
        dispatch(logoutSucces());
    }


    let links = (
        <ul className="navbar-nav ml-auto">
            <li className="nav-item">
                <Link className="nav-link"
                    to="/">{t('Events')}</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link"
                    to="/login">{t('Login')}</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link"
                    to="/signup">{t('Sign Up')}</Link>
            </li>
        </ul>
    );

    if (isLoggedIn) {
        let dropdownClass = 'dropdown-menu p-0 shadow';
        if (menuVisible) {
            dropdownClass += ' show';
        }
        links = (
            <div className="collapse navbar-collapse ">
                <ul className="navbar-nav ml-auto ">
                    <li className="nav-item mr-2">
                        <Link className="nav-link " style={{ fontSize: '20px', color: 'white' }}
                            to="/">{t('Events')}</Link>
                    </li>
                    <li className="nav-item dropdown" ref={menuArea} >
                        <div className="d-flex" style={{ cursor: 'pointer' }} onClick={() => setMenuVisible(true)}>
                            <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle m-auto" />
                            <span className="nav-link dropdown-toggle" style={{ fontSize: '20px', color: 'white' }}>
                                {displayName}
                            </span>
                        </div>
                        <div className={dropdownClass} style={{ cursor: 'pointer' }} >
                            <Link className="nav-link dropdown-item d-flex p-2"
                                onClick={() => setMenuVisible(false)}
                                style={{ color: 'black' }}
                                to={'/user/' + username}>
                                <span className="material-icons text-info mr-2">
                                    person
                                </span>
                                {t('My Profile')}
                            </Link>
                            <span className="nav-link dropdown-item d-flex p-2"
                                onClick={onLogoutSuccess}
                                style={{ cursor: 'pointer', color: 'black' }}>
                                <span className="material-icons text-danger mr-2">
                                    power_settings_new
                                </span>
                                {t('Logout')}
                            </span>
                        </div>
                    </li>


                </ul>
            </div >
        )
    }
    return (
        <div className="shadow-sm bg-dark mb-2 fixed-top">
            <nav className="navbar navbar-dark container navbar-expand">
                <Link className="navbar-brand" to="/" >
                    <img src={icon} width="50" alt="icon" /> TEHAPY
            </Link>
                {links}

            </nav>
        </div>

    );
}


export default TopBar;
