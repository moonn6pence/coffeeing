import React from 'react'

type NextButtonProps = {
  handleClick:()=>void;
}

export const NextButton = ({handleClick}:NextButtonProps)=>{
  return(

      <button 
        className='w-40 h-12 text-white bg-light-roasting text-xl'
        onClick={()=>handleClick()}
        >다음</button>

  )
}