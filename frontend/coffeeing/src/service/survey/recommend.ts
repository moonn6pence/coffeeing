import { publicRequest, privateRequest } from "util/axios";
import { API_URL } from "util/constants";

export const getSurveyResult = async (survey:any, isLogin:boolean)=>{
  try {
    if (isLogin) {
      const res =  await privateRequest
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
      console.log(res.data.data)
      return res.data.data
    } else {
      const res =  await publicRequest
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
      console.log(res.data.data)
      return res.data.data
    }
    
  } catch (error) {
    console.log('[fail to get recommendation]', error)
    throw error
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
    return true
  } catch (error) {
    console.log('[save preference fail', error)
    throw error
  }
}