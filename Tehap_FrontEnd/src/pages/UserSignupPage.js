import React, { useState } from 'react';
import Input from '../components/Input'
import { useTranslation } from 'react-i18next';
import ButtonWithProgress from '../components/ButtonWithProgress';
import { useApiProgress } from '../shared/ApiProgress';
import { useDispatch } from 'react-redux';
import { signupHandler } from '../redux/authActions';
import '../css/style.css';

const UserSignupPage = (props) => {

    const dispatch = useDispatch();
    const [proImage, setProImage] = useState();

    const [form, setForm] = useState({
        username: null,
        displayName: null,
        password: null,
        passwordRepeat: null,
        email: null,
        usersurname: null,
        university: null,
        branch: null,
        image: null,
    })
    const [errors, setErrors] = useState({});

    const onChange = event => {
        const { name, value } = event.target;

        setErrors((previousError) => ({ ...previousError, [name]: undefined }));

        setForm((previousForm) => {
            return {
                ...previousForm,
                [name]: value
            }
        });
    };

    const onChangeFile = (event) => {
        if (event.target.files.length < 1) {
            return;
        }
        console.log(event);
        const file = event.target.files[0];
        const fileReader = new FileReader();
        fileReader.onloadend = () => {
            setProImage(fileReader.result);
        }
        fileReader.readAsDataURL(file);
    }

    const onClickSignup = async event => {
        event.preventDefault();
        const { history } = props; //signuphandler
        const { push } = history; //signuphandler
        const { username, displayName, password, email, usersurname, university, branch, image } = form;

        if (proImage) {
            form.image = proImage.split(',')[1];
        }
        console.log(form.image);
        const body = {
            username,
            displayName,
            password,
            email,
            usersurname,
            university,
            branch,
            image,
        }
        try {
            const response = await dispatch(signupHandler(body));
            push('/login');
        } catch (error) {
            if (error.response.data.validationErrors) {
                setErrors(error.response.data.validationErrors);
            }
        }
    };
    const { t } = useTranslation();
    const { username: usernameError, displayName: displayNameError, password: passwordError, passwordRepeat, usersurname: usersurnameError, email: emailError, university: universityError, branch: branchError } = errors;
    const pendingApiCall = useApiProgress('post', '/api/createuser');

    let passwordRepeatError;
    if (form.password !== form.passwordRepeat) {
        passwordRepeatError = t('Password mismatch');
    }


    return (
        <div className="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
            <div className="card card0 border-0">
                <div className="row d-flex">
                    <div className="col-lg-2">
                    </div>
                    <div className="col-lg-8">
                        <h1 className="text-center">{t('Sign Up')}</h1>
                        <div className="card2 card border-0 px-1 py-3">
                            <Input type="file" onChange={onChangeFile} />
                            <div className="row">
                                <div className=" mb-4 px-3 col-lg-6">
                                    <form>
                                        <div className="form-group">
                                            <Input name="username" label={t('Username')} error={usernameError} onChange={onChange} />
                                            <Input name="usersurname" label={t('Surname')} error={usersurnameError} onChange={onChange} />
                                            <Input name="displayName" label={t('Display Name')} error={displayNameError} onChange={onChange} />
                                            <Input name="email" label={t('Email')} error={emailError} onChange={onChange} />
                                        </div>
                                    </form>
                                </div>
                                <div className=" mb-4 px-3 col-lg-6">
                                    <form>
                                        <div className="form-group">
                                            <Input name="password" label={t('Password')} error={passwordError} onChange={onChange} type="password" />
                                            <Input name="passwordRepeat" label={t('Password Repeat')} error={passwordRepeatError} onChange={onChange} type="password" />
                                            <Input name="university" label={t('University')} error={universityError} onChange={onChange} />
                                            <Input name="branch" label={t('Branch')} error={branchError} onChange={onChange} />
                                        </div>
                                    </form>
                                </div>

                            </div>
                            <ButtonWithProgress
                                onClick={onClickSignup}
                                disabled={pendingApiCall || passwordRepeatError !== undefined}
                                pendingApiCall={pendingApiCall}
                                text={t('Sign Up')} />
                        </div>

                    </div>
                    <div className="col-lg-2">
                    </div>
                </div>
            </div>
        </div>
    );
}


export default UserSignupPage;
