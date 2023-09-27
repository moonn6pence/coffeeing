import React, { useEffect, useState } from "react";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { privateRequest } from "util/axios";
import { API_URL } from "util/constants";

export const RecResultPage  = ()=>{
  const [result, setResult] = useState();

  const sendPreference = async ()=>{
    try {
      return await savePreference();
    } catch (error) {
      console.error('Error sending preference:', error)
    }
  }
  
  useEffect( ()=>{
    sendPreference();
  },[])
  // useEffect(()=>{
  //   console.log(survey)
  //   privateRequest
  //   .post(`${API_URL}/survey/save`, 
  //     {},
  //     {
  //       params: {
  //       roast:survey.roasting,
  //       acidity:survey.acidity,
  //       body:survey.body,
  //       flavorNote:survey.flavorNote,
  //       isCapsule:survey.isCapsule,
  //       machineType:survey.machine
  //       } 
  //     }
  //   )
  //   .then((res)=>{
  //     console.log(res.data)
  //     setResult(res.data.data)
  //   })
  //   .catch((err)=>{
  //     console.log(err)
  //   })
  // },[])

  // useEffect(()=>{
  //   async () => {
  //     const res = await savePreference({
  //       roast:survey.roasting,
  //       acidity:survey.acidity,
  //       body:survey.body,
  //       flavorNote:survey.flavorNote,
  //       isCapsule:true,
  //       machineType:survey.machine
  //     })
  //     if (res) {
  //       console.log('[save preference succeeded]')
  //     } else {
  //       console.log('[save preference failed')
  //     }
  //   }
  // },[])

  // useEffect(()=>{
  //   async () => {
  //     const surveyResult = await getSurveyResult({
  //       roast:survey.roasting,
  //       acidity:survey.acidity,
  //       body:survey.body,
  //       flavorNote:survey.flavorNote,
  //       isCapsule:true,
  //       machineType:survey.machine
  //     })
  //     // setResult(surveyResult)
  //   }
  // },[])

  return(
    <div>아</div>
  )
}