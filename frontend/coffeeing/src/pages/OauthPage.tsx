import React from 'react';

import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';
import { setMemberToken } from "store/memberSlice";

export const OauthPage = () => {
    const dispatch = useDispatch<AppDispatch>();
    const oauthLogin = () => {
        const urlParams = new URLSearchParams(window.location.search);
        const accessToken = urlParams.get("at") || '';
        const refreshToken = urlParams.get("rt") || '';

        dispatch(setMemberToken({
            accessToken,
            refreshToken,
            "grantType": "Bearer"
        }));

        window.location.replace('/');
    }

    oauthLogin();
    return (
        <div>
        </div>
    )
}