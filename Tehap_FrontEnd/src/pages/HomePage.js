import React from 'react';
import { useSelector } from 'react-redux';
import ActivitySubmit from '../components/ActivitySubmit';
import Activities from '../components/Activities';
import { useTranslation } from 'react-i18next';

const HomePage = () => {
    const { isLoggedIn } = useSelector(store => ({ isLoggedIn: store.isLoggedIn }));
    const { t } = useTranslation();

    return (
        <div className="container">
            <div className="row">
                {isLoggedIn &&
                    <div className="col-md-12">
                        <h1 className="my-4 text-center">{t('Activities')}</h1>
                        <ActivitySubmit />
                    </div>
                }

                <div className="col-md-12">
                    <br></br>
                    <hr></hr>
                    <br></br>
                    <  Activities />
                </div>
            </div>
        </div>


        /*
            <div className="container">
                <UserList />
            </div>
        */
    );
};

export default HomePage;