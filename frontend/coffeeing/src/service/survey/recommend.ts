import { isAxiosError } from "axios"; 
import { publicRequest, privateRequest } from "util/axios";
import { ApiSuccessResponse } from "types/apis";
import { API_URL } from "util/constants";
import { surveyResult, mySurvey } from "./types";
import { RootState } from "store/store";
import { useSelector } from "react-redux";
// import { savePreference } from 'service/survey/recommend';

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

export const savePreference = async ()=>{
  const survey=useSelector((state:RootState)=>state.survey)
  try {
    const response = await privateRequest
    .post(`${API_URL}/survey/save`, {
      params:{
        roast:survey.roasting,
        acidity:survey.acidity,
        body:survey.body,
        flavorNote:survey.flavorNote,
        isCapsule: survey.isCapsule,
        machineType:survey.machine,
      }
    })
  } catch (error) {
    console.log('[save preference fail', error)
    throw error
  }
}

// export const savePreference = ():Promise<boolean>=>{
//   const survey=useSelector((state:RootState)=>state.survey)
//   return privateRequest
//   .post(`${API_URL}/survey/save`, {
//     params:{
//       roast:survey.roasting,
//       acidity:survey.acidity,
//       body:survey.body,
//       flavorNote:survey.flavorNote,
//       isCapsule: survey.isCapsule,
//       machineType:survey.machine,
//     }
//   })
//   .then(()=>{
//     return true
//   })
//   .catch((error)=>{
//     if (!isAxiosError(error)) {
//       console.error('[save mypreference request fail]')
//     }
//     return false
//   })
// }