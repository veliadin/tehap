import React, { useState, useEffect } from 'react';
import Input from '../components/Input'
import { useTranslation } from 'react-i18next'
import ButtonWithProgress from '../components/ButtonWithProgress';
import { useApiProgress } from '../shared/ApiProgress'
import { useDispatch } from 'react-redux';
import { loginHandler, loginWithSocialHandler } from '../redux/authActions';
import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';
import '../css/style.css';
import { FaFacebook, FaGoogle } from "react-icons/fa";
import { Link, useRouteMatch } from 'react-router-dom';

const UserLoginPage = (props) => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [error, setError] = useState();

    var form = {
        providerId: null, //response.googleId
        username: null,//response.profileObj.givenName
        displayName: null, // response.name
        imageUrl: null, //response.profileObj.imageUrl
        email: null,//response.profileObj.email
        provider: null //response.tokenObj.idpId
    };

    const dispatch = useDispatch();

    useEffect(() => { // username veya password'da bir değişim olduğunda, errorlar undefined oluyor.
        setError(undefined);
    }, [username, password])


    const onClickLogin = async event => {
        event.preventDefault();
        const creds = {
            username,
            password
        };
        const { history } = props;
        const { push } = history;

        setError(undefined);
        try {
            await dispatch(loginHandler(creds));
            push('/')
        } catch (apiError) {
            console.log(apiError.response);
            setError(apiError.response.data.message);
        }
    };
    //Facebook Area S
    const componentClicked = data => {
        console.log("data", data);
    };

    const responseFacebook = response => {
        console.log(response);

        if (response.accessToken) {
            const { history } = props;
            const { push } = history;
            setError(undefined);
            try {
                form = {
                    providerId: response.id, //response.googleId
                    username: response.name,//response.profileObj.givenName
                    displayName: response.name, // response.name
                    imageUrl: response.picture.data.url, //response.profileObj.imageUrl
                    email: response.email,//response.profileObj.email
                    provider: response.graphDomain//response.tokenObj.idpId
                };
                console.log(form);
                dispatch(loginWithSocialHandler(form));
                push('/');
            } catch (error) {
                setError("Something wrong in Facebook Login");
            }

        }
    };
    //Facebook Area F Google S
    const responseGoogle = (response) => {
        console.log(response)

        if (response.accessToken) {
            const { history } = props;
            const { push } = history;
            setError(undefined);
            try {

                form = {
                    providerId: response.googleId, //response.googleId
                    username: response.profileObj.givenName,//response.profileObj.givenName
                    displayName: response.profileObj.givenName, // response.name
                    imageUrl: response.profileObj.imageUrl, //response.profileObj.imageUrl
                    email: response.profileObj.email,//response.profileObj.email
                    provider: response.tokenObj.idpId//response.tokenObj.idpId
                };
                console.log(form);
                dispatch(loginWithSocialHandler(form));
                push('/');
            } catch (error) {
                setError("Something wrong in Google Login");
            }

        }
    };

    //Google F
    const { t } = useTranslation();
    const pendingApiCall = useApiProgress('post', '/api/auth');
    const buttonEnabled = username && password;
    return (
        <div class="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
            <div class="card card0 border-0">
                <div class="row d-flex">
                    <div class="col-lg-6">
                        <div class="card1 pb-5 card-body">
                            <h1 className="card-title">TEHAPY</h1>
                            <p className="card-text"> {t('Tehapy Description')}</p>
                            <p className="card-text"> {t('Tehapy Activity Description')}</p>
                            <p className="card-text"> {t('Tehapy User Description')}</p>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="card2 card border-0 px-4 py-5">
                            <div class="row mb-4 px-3">
                                <div class="text-center mr-4" style={{ display: 'flex', flexWrap: 'wrap' }}>

                                    <FacebookLogin
                                        appId="2978237078953063"
                                        fields="name,email,picture"
                                        onClick={componentClicked}
                                        callback={responseFacebook}
                                        cssClass="btnFacebook"
                                        icon={<FaFacebook color='white' size='1.4rem'></FaFacebook>}
                                        textButton={t('Sign In with Facebook')}
                                    />

                                    <GoogleLogin
                                        clientId="181124231251-7hfpmban9e79jkgllfph3rnp0i8o6t27.apps.googleusercontent.com"
                                        buttonText="Login"
                                        onSuccess={responseGoogle}
                                        onFailure={responseGoogle}
                                        cookiePolicy={'single_host_origin'}
                                        className="btnGoogle"
                                        buttonText={t('Sign In with Google')}
                                    />
                                </div>
                            </div>
                            <div class="row px-3 mb-4">
                                <div class="line"></div> <small class="or text-center">Or</small>
                                <div class="line"></div>
                            </div>
                            <Input label={t('Username')} onChange={event => setUsername(event.target.value)} />
                            <Input label={t('Password')} type="password" onChange={event => setPassword(event.target.value)} />
                            {error && <div className="alert alert-danger" role="alert">{error}</div>}

                            <ButtonWithProgress
                                onClick={onClickLogin}
                                disabled={!buttonEnabled || pendingApiCall}
                                pendingApiCall={pendingApiCall}
                                text={t('Login')} />

                            <div class="row mb-4 px-3"> <small class="font-weight-bold">Don't have an account? <a class="text-danger"></a></small> </div>
                        </div>
                    </div>
                </div>

            </div>
        </div >
    );
}


export default UserLoginPage;