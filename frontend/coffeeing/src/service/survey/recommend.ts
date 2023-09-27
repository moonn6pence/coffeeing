import { publicRequest, privateRequest } from "util/axios";
import { API_URL } from "util/constants";
import { surveyResult, mySurvey } from "./types";

export const getSurveyResult = async (survey:any)=>{
  try {
    await publicRequest
    .post(`${API_URL}/survey/recommend`,{},{
      params:{
        roast:survey.roasting,
        acidity:survey.acidity,
        body:survey.body,
        flavorNote:survey.flavorNote,
        isCapsule: survey.isCapsule,
        machineType:survey.machine,
      }
    })
    .then((res)=>{
      return res.data.data
    })
  } catch (error) {
    console.log('[fail to get recommendation]', error)
    return false
  }
}

export const savePreference = async (survey:any)=>{
  try {
    await privateRequest
    .post(`${API_URL}/survey/save`,{}, {
      params:{
        roast:survey.roasting,
        acidity:survey.acidity,
        body:survey.body,
        flavorNote:survey.flavorNote,
        isCapsule: survey.isCapsule,
        machineType:survey.machine,
      }
    })
    return '[save preference succeeded]'
  } catch (error) {
    console.log('[save preference fail', error)
    throw error
  }
}