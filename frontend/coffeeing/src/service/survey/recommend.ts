import { isAxiosError } from "axios"; 
import { publicRequest, privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { surveyResult, mySurvey } from "./types";

type MyRecommendationApiData = ApiSuccessResponse<surveyResult>

export const getSurveyResult = (mySurvey:mySurvey):Promise<void|surveyResult>=> {
  return privateRequest
  .post<MyRecommendationApiData>(`${API_URL}/survey/recommend`,mySurvey)
  .then((res)=>{
    return Promise.resolve(res.data.data)
  })
  .catch((error)=>{
    if (!isAxiosError(error)) {
      console.error('[recommendation request fail]')
    }
  })
}