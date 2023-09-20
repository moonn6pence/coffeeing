import { isAxiosError } from "axios"; 
import { privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { MyInfo } from "./types"

const MEMBER_PATH = "member";

export type MyInfoApiData = ApiSuccessResponse<MyInfo>;

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