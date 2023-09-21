import water from '../../assets/survey/body/water.png'
import icedTea from '../../assets/survey/body/iced_tea_1.png'
import milk from '../../assets/survey/body/milk.png'
import unknownBody from '../../assets/survey/body/unknownBody.png'

import React,{Dispatch, SetStateAction, useState}  from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { NextButton } from './NextButton';
import { addCurrentPage } from 'store/surveySlice';
import { saveBody } from 'store/surveySlice';

export const BodySelect = () => {
  const survey = useSelector((state:RootState)=>state.survey)
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const dispatch = useDispatch<AppDispatch>();
  const [selectedWater, setSelectedWater] = useState(false);
  const [selectedIcedTea, setSelectedIcedTea] = useState(false);
  const [selectedMilk, setSelectedMilk] = useState(false);
  const [selectedUnknown, setSelectedUnknown] = useState(false)
  const [myBody, setMyBody]= useState(-1)

  const data = [
    {src:water, label:'light',isSelected:selectedWater, setIsSelected:setSelectedWater,num:0.3},
    {src:icedTea, label:'medium',isSelected:selectedIcedTea, setIsSelected:setSelectedIcedTea,num:0.6},
    {src:milk, label:'heavy',isSelected:selectedMilk, setIsSelected:setSelectedMilk,num:0.9},
    {src:unknownBody, label:'잘 모르겠어요',isSelected:selectedUnknown, setIsSelected:setSelectedUnknown,num:0},
  ]

  const handleBodySelect = (num:number, isSelected:boolean, setIsSelected:Dispatch<SetStateAction<boolean>>) =>{
    if (myBody===-1) {
      setMyBody(num)
      setIsSelected(!isSelected)
    } else if (myBody===num) {
      setMyBody(-1)
      setIsSelected(!isSelected)
    } else {
      alert('하나만 선택해주세요')
    }
  }
  const handleBodyClick = () => {
    if (myBody===-1) {
      alert('선호하는 바디감을 하나만 선택해주세요')
    } else {
      dispatch(addCurrentPage())
      dispatch(saveBody(myBody))
    }
  }

  return(
    <div className='flex flex-col items-center gap-10 mt-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='font-bold'>바디감 단계 선택</p>
        <p className='relative w-560px h-2.5 rounded-lg bg-process-bar'>
          <p className={`absolute botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-light-roasting`}></p>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className='flex flex-row gap-10'>
        {
          data.map((item) => {
            const { src, label, isSelected, setIsSelected, num } = item;
            return (
              <div className={`w-64 h-60 flex flex-col items-center ${isSelected ? 'bg-select-img' : ''} rounded-xl`} key={num}>
                <img
                  className={`w-52 h-52 origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
                  src={src}
                  onClick={()=>handleBodySelect(num, isSelected, setIsSelected)}
                />
                <p>{label}</p>
              </div>
            );
          })
        }
      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleBodyClick}/>
    </div>
  )
}