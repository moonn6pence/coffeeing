import React from 'react'

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