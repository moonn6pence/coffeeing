import { isAxiosError } from "axios"; 
import { publicRequest, privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { PostFeedReq, PostFeedRes } from "./types";

const FEED_PATH = "feeds";

type PostFeedApiData = ApiSuccessResponse<PostFeedRes>;

export const postFeed = (params: PostFeedReq):Promise<void|PostFeedRes> => {
    return privateRequest
    .post<PostFeedApiData>(`${API_URL}/${FEED_PATH}`, params)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[My info reust fail]: Unknown error");
        }
    });
}