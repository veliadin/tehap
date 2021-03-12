import axios from 'axios';

export const signup = (body) => {
    return axios.post('/api/createuser', body)
};

export const login = creds => {
    return axios.post('/api/auth', {}, { auth: creds });
};

export const loginWithSocial = (body) => {
    return axios.post('/api/loginSocial', body);
};

export const changeLanguage = language => {
    axios.defaults.headers['accept-language'] = language;
};

export const getUsers = (page = 0, size = 3) => {
    return axios.get(`/api/getusers?page=${page}&size=${size}`);
}

export const getUser = username => {
    return axios.get(`/api/users/${username}`);
}

export const updateUser = (username, body) => {
    const ret = axios.put(`/api/users/${username}`, body);
    console.log(ret);
    return ret;
}

export const setAuthorizationHeader = ({ username, password, isLoggedIn }) => {
    if (isLoggedIn) {
        const authorizationHeaderValue = `Basic ${btoa(username + ':' + password)}`
        axios.defaults.headers['Authorization'] = authorizationHeaderValue;
    } else {
        delete axios.defaults.headers['Authorization']
    }
}

export const createActivity = body => {
    return axios.post('/api/createActivity', body)
}

export const getActivities = (username, page = 0, size = 2) => {
    const path = username ? `/api/users/${username}/activities?page=` : `/api/getActivities?page=`;
    return axios.get(path + page);
}

export const getOldActivities = (id, username) => {
    const path = username ? `/api/users/${username}/activities/${id}` : `/api/getActivities/${id}`;
    return axios.get(path);
}

export const getNewActivityCount = (id, username) => {
    const path = username ? `/api/users/${username}/activities/${id}?count=true` : `/api/getActivities/${id}?count=true`;
    return axios.get(path);
}

export const getNewActivities = (id, username) => {
    const path = username ? `/api/users/${username}/activities/${id}?direction=after` : `/api/getActivities/${id}?direction=after`
    return axios.get(path)
}

export const postActivityAttachment = attachment => {
    return axios.post('/api/activity-attachments', attachment)
}

export const deleteActivity = id => {
    return axios.delete(`/api/deleteActivity/${id}`)
}

export const deleteUser = username => {
    return axios.delete(`/api/users/delete/${username}`);
}

export const follow = (username) => {
    return axios.post(`/api/follow/${username}`)
}

export const unfollow = (username) => {
    return axios.delete(`/api/unfollow/${username}`)
}

export const getFollowersCount = (username) => {
    return axios.get(`/api/getFollowersCount/${username}`)
}

export const getFollowingCount = (username) => {
    return axios.get(`/api/getFollowingCount/${username}`)
}

export const getTheUserFollow = (username) => {
    return axios.get(`/api/getTheUserFollow/${username}`)
}

export const saveAttendedUser = (activityId) => {
    return axios.post(`/api/saveAttendedUser/${activityId}`);
}