import React, { useEffect } from 'react';

import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';
import { setMemberToken, setMyInfo } from "store/memberSlice";
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
            if(result) {
                dispatch(setMyInfo(result));

                if(result.state === MemberState.BEFORE_ADDITIONAL_DATA) {
                    window.location.replace("/signup/additonal-info");
                    return;
                }

                if(result.isAfterSurvey) {
                    window.location.replace("/beans");
                    return;
                } else {
                    window.location.replace("/recommend-main");
                    return;
                }

            }
            window.location.replace("/login");
        }
        oauthLogin();
    }, []);
    
    return (
        <div>
        </div>
    )
}