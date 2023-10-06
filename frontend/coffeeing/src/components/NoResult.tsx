import React from "react";
import noResult from '../assets/search/NoFilterResult.svg'

type NoResultProps = {
  label:string
}

export const NoResult = ({label}:NoResultProps)=>{
  return(
    <div className="flex flex-col items-center">
      <img src={noResult} className="mx-auto"/>
      <p className="text-3xl">{label}</p>
    </div>
  )
}