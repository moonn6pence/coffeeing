import { isAxiosError } from "axios"; 
import { privateRequest } from "util/axios";
import { ApiSuccessResponse, ToggleResult } from "types/apis";
import { API_URL } from "util/constants";
import { PostFeedReq, PostFeedRes, GetFeedRes } from "./types";

const FEED_PATH = "feeds";

type PostFeedApiData = ApiSuccessResponse<PostFeedRes>;
type GetFeedApiData = ApiSuccessResponse<GetFeedRes>;
type PostFeedLikeData = ApiSuccessResponse<ToggleResult>;

export const postFeed = (params: PostFeedReq):Promise<void|PostFeedRes> => {
    return privateRequest
    .post<PostFeedApiData>(`${API_URL}/${FEED_PATH}`, params)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[Post feed request fail]: Unknown error");
        }
    });
}

export const postFeedLike = (feedId: number):Promise<void|ToggleResult> => {
    return privateRequest
    .post<PostFeedLikeData>(`${API_URL}/${FEED_PATH}/${feedId}/like`)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[Post Feed Like request fail]: Unknown error");
        }
    });
}

export const getFeeds = (cursor: number|undefined, size: number):Promise<void|GetFeedRes> => {
    return privateRequest
    .get<GetFeedApiData>(`${API_URL}/${FEED_PATH}`, {
        params: {
            cursor,
            size
        }
    })
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[Get Feeds request fail]: Unknown error");
        }
    });
};

export const deleteFeeds = (feedId: number):Promise<boolean>=>{
    return privateRequest
    .delete(`${API_URL}/${FEED_PATH}/${feedId}`).then((res)=>{
        return true
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[delete request fail]: Unknown error");
        }
        return false;
    });
}