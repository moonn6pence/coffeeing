import React, { useEffect } from 'react';

import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';
import { setMemberToken } from "store/memberSlice";
import { getMyInfo } from "../service/member/member"
import { MemberState } from "service/member/types";

export const OauthPage = () => {
    const dispatch = useDispatch<AppDispatch>();

    useEffect(()=>{
        const oauthLogin = async () => {
            const urlParams = new URLSearchParams(window.location.search);
            const accessToken = urlParams.get("at") || '';
            const refreshToken = urlParams.get("rt") || '';
    
            dispatch(setMemberToken({
                accessToken,
                refreshToken,
                "grantType": "Bearer"
            }));
            
            const result = await getMyInfo();
            if(result && result.state == MemberState.BEFORE_ADDITIONAL_DATA) {
                window.location.replace("/signup/additonal-info");
            } 
            
            if(result && result.state == MemberState.BEFORE_RESEARCH) {
                window.location.replace("/recommend-main");
            }
            window.location.replace("/beans");
        }
        oauthLogin();
    }, []);
    
    return (
        <div>
        </div>
    )
}