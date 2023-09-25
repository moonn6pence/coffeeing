import { isAxiosError } from "axios"; 
import { publicRequest, privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { MyInfo, UniqueNickname, OnboardRequest, OnboardResult } from "./types"

const MEMBER_PATH = "member";

type MyInfoApiData = ApiSuccessResponse<MyInfo>;
type CheckUniqueNicknameApiData = ApiSuccessResponse<UniqueNickname>;
type OnboardApiData = ApiSuccessResponse<OnboardResult>;

export const getMyInfo = ():Promise<void|MyInfo> => {
    return privateRequest
    .get<MyInfoApiData>(`${API_URL}/${MEMBER_PATH}/my-info`)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[My info reust fail]: Unknown error");
        }
    });
}

export const checkUniqueNickname = (nickname: string):Promise<void|UniqueNickname> => {
    return publicRequest
    .get<CheckUniqueNicknameApiData>(`${API_URL}/${MEMBER_PATH}/unique-nickname?nickname=${nickname}`)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[checku unique nickname fail]: Unknown error");
        }
    })
}

export const postOnboard = (onboard: OnboardRequest):Promise<void|OnboardResult> => {
    return privateRequest
    .post<OnboardApiData>(`${API_URL}/${MEMBER_PATH}/onboard`, onboard)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[onboard request fail]: Unknown error");
        }
    })
}