import { isAxiosError } from "axios"; 
import { publicRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { SignUpMemberInfo, SignInMemberInfo } from "./types";

const AUTH_PATH = "auth";

export type SignUpApiData = ApiSuccessResponse<SignUpMemberInfo>;
export type SignInApiData = ApiSuccessResponse<SignInMemberInfo>;

export interface MemberParams {
    email: string,
    password: string
}

export const signIn = (params: MemberParams):Promise<void|SignInMemberInfo> => {
    return publicRequest
    .post<SignInApiData>(`${API_URL}/${AUTH_PATH}/sign-in`, params)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[sign in fail]: Unknown error");
        }
    });
}

export const signUp = (params: MemberParams):Promise<void|SignUpMemberInfo> => {
    return publicRequest
        .post<SignUpApiData>(`${API_URL}/${AUTH_PATH}/sign-up`, params)
        .then((res)=>{
            return Promise.resolve(res.data.data);
        }).catch((error)=>{
            if(!isAxiosError(error)) {
                console.error("[sign up fail]: Unknown error");
            }
        });
}

export const reissueToken = (reissueToken: string) => {
    return publicRequest.post(`${API_URL}/${AUTH_PATH}/reissue`, {
        reissueToken
    }).then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch(async (error)=>{
        if(!isAxiosError(error) || !error.response?.data || !error.config) {
            console.error("[token reissue]: Unknown error");
        }

        if(error.response.status >= 500) {
            console.error("[expried reissue token]: token is expried");
            /**
             * TODO 
             * 
             * logout
             */
        }

        return Promise.reject(error);
    });
};