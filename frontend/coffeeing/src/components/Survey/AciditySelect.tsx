import React,{Dispatch, SetStateAction, useState} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import noAcid from '../../assets/survey/acidity/noAcid.png'
import lowAcid from '../../assets/survey/acidity/lowAcid.png'
import mediumAcid from '../../assets/survey/acidity/mediumAcid.png'
import highAcid from '../../assets/survey/acidity/highAcid.png'
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage,saveAcidity } from 'store/surveySlice';
import { NextButton } from './NextButton';

export const AciditySelect = () =>{
  const survey = useSelector((state:RootState)=>state.survey)
  const dispatch = useDispatch<AppDispatch>();
  const [selectedNoAcid, setSelectedNoAcid] = useState(false)
  const [selectedLowAcid, setSelectedLowAcid] = useState(false)
  const [selectedMediumAcid, setSelectedMediumAcid] = useState(false)
  const [selectedHighAcid, setSelectedHighAcid] = useState(false)
  const [myAcidity, setMyAcidity] = useState(-1)

  // 데이터
  const data = [
    {src:noAcid, label:'없음', isSelected:selectedNoAcid, setIsSelected:setSelectedNoAcid,num:0},
    {src:lowAcid, label:'없음', isSelected:selectedLowAcid, setIsSelected:setSelectedLowAcid,num:0.4},
    {src:mediumAcid, label:'없음', isSelected:selectedMediumAcid, setIsSelected:setSelectedMediumAcid,num:0.6},
    {src:highAcid, label:'없음', isSelected:selectedHighAcid, setIsSelected:setSelectedHighAcid,num:0.8},
  ]
  // 산미 단계 이미지 클릭 시
  const handleAciditySelect = (num:number,isSelected:boolean,setIsSelected:Dispatch<SetStateAction<boolean>>)=>{
    if(myAcidity===-1){
      setMyAcidity(num)
      setIsSelected(!isSelected)
    } else if (myAcidity===num) {
      setMyAcidity(-1)
      setIsSelected(!isSelected)
    } else {
      alert('하나만 선택해주세요')
    }
  }
  // 다음 버튼 클릭 시
  const handleAciditySubmit = ()=>{
    // 선택 안 했을 때
    if (myAcidity===-1) {
      alert('선호하는 로스팅 단계를 하나 선택해주세요')
    } 
    // 선택 했을 때 - 다음 페이지로 & 산미 정보 저장
    else {
      dispatch(addCurrentPage())
      dispatch(saveAcidity(myAcidity))
    }
  }
  return(
    <div className='flex flex-col items-center gap-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='font-bold'>산미 단계 선택</p>
        <p className='w-560px h-2.5 rounded-lg bg-process-bar'></p>
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
                onClick={()=>handleAciditySelect(num,isSelected,setIsSelected)}
              />
              <p>{label}</p>
            </div>
          );
        })
      }
      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleAciditySubmit} />
    </div>
  )
}