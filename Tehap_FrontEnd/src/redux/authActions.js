import * as ACTIONS from './Constants'
import { login, signup, loginWithSocial } from '../api/apiCalls';

export const logoutSucces = () => {
    return {
        type: ACTIONS.LOGOUT_SUCCESS
    };
}

export const loginSuccess = authState => {
    return {
        type: ACTIONS.LOGIN_SUCCESS,
        payload: authState
    }
}

export const updateSuccess = ({ displayName, image }) => {
    return {
        type: ACTIONS.UPDATE_SUCCESS,
        payload: {
            displayName,
            image
        }
    }
}

export const loginHandler = (credentials) => {
    return async function (dispatch) {
        const response = await login(credentials);
        const authState = {
            ...response.data,
            password: credentials.password
        };

        dispatch(loginSuccess(authState));
        return response;
    }
}

export const loginWithSocialHandler = (user) => {
    return async function (dispatch) {
        const response = await loginWithSocial(user);
        const authState = {
            ...user
        };

        dispatch(loginSuccess(authState));
        return response;
    }
}

export const signupHandler = (user) => {
    return async function (dispatch) {
        const response = await signup(user);
        //await dispatch(loginHandler(user)); //signup sonrası login için kullanılabilir ancak component üzerinde değişikliğe ihtiyaç olur.
        return response;
    }
}