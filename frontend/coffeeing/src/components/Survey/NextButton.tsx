import React from 'react'
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { minusCurrentPage } from 'store/surveySlice'

type NextButtonProps = {
  handleClick:()=>void;
}

export const NextButton = ({handleClick}:NextButtonProps)=>{
  return(
    <button 
      className='w-40 h-12 text-white font-bold rounded-3xl bg-half-light text-xl'
      onClick={()=>handleClick()}
      >다음</button>
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