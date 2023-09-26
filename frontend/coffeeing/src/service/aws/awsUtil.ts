import { isAxiosError } from "axios"; 
import { privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { S3Info } from "./types"

const AWS_PATH = "aws";

type S3ApiData = ApiSuccessResponse<S3Info>;

export const getS3PreSignedURL = ():Promise<void|S3Info> => {
    return privateRequest
    .get<S3ApiData>(`${API_URL}/${AWS_PATH}/img`)
    .then((res)=>{
        return Promise.resolve(res.data.data);
    }).catch((error)=>{
        if(!isAxiosError(error)) {
            console.error("[S3ApiData requst fail]: Unknown error");
        }
    });
}