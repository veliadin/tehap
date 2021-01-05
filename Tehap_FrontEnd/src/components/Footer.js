import React from 'react';
import { useTranslation } from 'react-i18next';
import { changeLanguage } from '../api/apiCalls';
import "react-icons/fa";

const Footer = () => {

    const { i18n } = useTranslation();
    const onChangeLanguage = language => {
        i18n.changeLanguage(language);
        changeLanguage(language)
    }

    var stylex = {
        position: "fixed",
        left: "0",
        bottom: "0",
        height: "40px",
        width: "100%",
        marginTop: 20,
    }

    return (

        <footer className="py-5 bg-dark" style={{ stylex }}>
            <div className="container">
                <p className="m-0 text-center text-white">Created By Veli Adin - Copyright &copy; 2020. All rights reserved.
                 <img src="https://www.countryflags.io/tr/flat/24.png"
                        alt="Turkish Flag"
                        onClick={() => onChangeLanguage('tr')}
                        style={{ cursor: 'pointer' }}></img>

                    <img src="https://www.countryflags.io/us/flat/24.png"
                        alt="USA Flag"
                        onClick={() => onChangeLanguage('en')}
                        style={{ cursor: 'pointer' }}></img></p>


            </div>
        </footer>

    );
};

export default Footer;