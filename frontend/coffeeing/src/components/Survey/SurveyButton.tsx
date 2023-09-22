import React from 'react'
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { minusCurrentPage } from 'store/surveySlice'

type NextButtonProps = {
  handleClick:()=>void;
  label:string
}

export const NextButton = ({handleClick, label}:NextButtonProps)=>{
  return(
    <button 
      className='w-40 h-12 text-white font-bold rounded-3xl bg-half-light text-xl'
      onClick={()=>handleClick()}
      >{label}</button>
  )
}

export const BackButton = ()=>{
  const dispatch = useDispatch<AppDispatch>();
  return(
    <button
      className='w-40 h-12 text-white font-bold rounded-3xl bg-half-light text-xl'
      onClick={()=>dispatch(minusCurrentPage())}
    >이전</button>
  )
}
