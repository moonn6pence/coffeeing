import React from "react";
import noResult from '../assets/search/NoFilterResult.svg'

type NoResultProps = {
  label:string
}

export const NoResult = ({label}:NoResultProps)=>{
  return(
    <div>
      <p className="text-3xl">{label}</p>
      <img src={noResult}/>
    </div>
  )
}