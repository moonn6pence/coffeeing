import axios from 'axios';
import store from 'store/store';
import { API_URL } from './constants';

export const publicRequest = axios.create({
    baseURL: API_URL,
    withCredentials: true,
    headers: {
        'Access-Control-Allow-Origin': 'http://localhost:3000',
        'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        'Access-Control-Allow-Credentials': true,
        'Content-Type': 'application/json'
    }
});

export const privateRequest = axios.create({
    baseURL: API_URL,
    withCredentials: true
});

privateRequest.interceptors.request.use(
    (config) => {
        const accessToken = store.getState().member.accessToken;
        const grantType = store.getState().member.grantType;
        config.headers.Authorization = `${grantType} ${accessToken}`;
        return config;
    }
)