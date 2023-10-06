import { isAxiosError } from "axios"; 
import { publicRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { Tag } from "./types"

const SEARCH_PATH = "search";

type TagResult = {
    tags: Tag[]
}

type TagSearchData = ApiSuccessResponse<TagResult>;

export const getTagsByKeyword = (keyword: string):Promise<void|TagResult> => {
    return publicRequest
    .get<TagSearchData>(`${API_URL}/${SEARCH_PATH}/tags?keyword=${keyword}`)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[My info reust fail]: Unknown error");
        }
    });
}