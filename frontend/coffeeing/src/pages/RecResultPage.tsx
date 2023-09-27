import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { RootState } from "store/store";
import { privateRequest } from "util/axios";
import { API_URL } from "util/constants";

export const RecResultPage  = ()=>{
  const [rec, setRec] = useState([]);
  const survey =useSelector((state:RootState)=>state.survey);
  const sendPreference = async ()=>{
    const result = await savePreference(survey);
    console.log(result)
  }
  const getPreference = async ()=>{
    const result = await getSurveyResult(survey);
    // setRec(result)
  }
  useEffect( ()=>{
    sendPreference();
  },[])

  return(
    <div>아</div>
  )
}