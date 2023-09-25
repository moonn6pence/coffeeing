import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "store/store";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { privateRequest } from "util/axios";
import { API_URL } from "util/constants";

export const RecResultPage  = ()=>{
  const survey = useSelector((state:RootState)=>state.survey)
  const [result, setResult] = useState();

  useEffect(()=>{
    console.log(survey)
    privateRequest
    .post(`${API_URL}/survey/save`, 
      {},
      {
        params: {
        roast:survey.roasting,
        acidity:survey.acidity,
        body:survey.body,
        flavorNote:survey.flavorNote,
        isCapsule:survey.isCapsule,
        machineType:survey.machine
        } 
      }
    )
    .then((res)=>{
      console.log(res.data)
      setResult(res.data.data)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[])

  // useEffect(()=>{
  //   async () => {
  //     const postSave = await savePreference({
  //       roast:survey.roasting,
  //       acidity:survey.acidity,
  //       body:survey.body,
  //       flavorNote:survey.flavorNote,
  //       isCapsule:true,
  //       machineType:survey.machine
  //     })
  //     console.log(postSave)
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