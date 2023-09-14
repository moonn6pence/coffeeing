import React,{Dispatch, SetStateAction, useState} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import lightRoast from '../../assets/survey/lightRoast.png'
import mediumRoast from '../../assets/survey/mediumRoast.png'
import darkRoast from '../../assets/survey/darkRoast.png'
import unknownRoast from '../../assets/survey/unknownRoast.png'
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveRoasting } from 'store/surveySlice';
import { NextButton } from './NextButton';


export const RoastingSelect = () =>{
  const survey = useSelector((state:RootState)=>state.survey)
  const dispatch = useDispatch<AppDispatch>();
  const [selectedLight, setSelectedLight] = useState(false)
  const [selectedMedium, setSelectedMedium] = useState(false)
  const [selectedDark, setSelectedDark] = useState(false)
  const [selectedUnknown, setSelectedUnknown] = useState(false)
  const [myRoast, setMyRoast] = useState(0)

  // 데이터
  const data = [
    {src:lightRoast, label:'라이트 로스팅', isSelected:selectedLight,setIsSelected:setSelectedLight,num:0},
    {src:mediumRoast, label:'미디엄 로스팅', isSelected:selectedMedium,setIsSelected:setSelectedMedium,num:1},
    {src:darkRoast, label:'라이트 로스팅', isSelected:selectedDark,setIsSelected:setSelectedDark,num:2},
    {src:unknownRoast, label:'라이트 로스팅', isSelected:selectedUnknown,setIsSelected:setSelectedUnknown,num:3},
  ]
  // 로스팅 단계 이미지 클릭 시
  const handleRoastSelect = (num:number,isSelected:boolean,setIsSelected:Dispatch<SetStateAction<boolean>>)=>{
    if(myRoast===0){
      setMyRoast(num)
      setIsSelected(!isSelected)
    } else if (myRoast===num) {
      setMyRoast(0)
      setIsSelected(!isSelected)
    } else {
      alert('하나만 선택해주세요')
    }
  }
  // 다음 버튼 클릭 시
  const handleRoastSubmit = ()=>{
    // 선택 안 했을 때
    if (myRoast===0) {
      alert('선호하는 로스팅 단계를 하나 선택해주세요')
    } 
    // 선택 했을 때 - 다음 페이지로 & 로스팅 정보 저장
    else {
      dispatch(addCurrentPage())
      dispatch(saveRoasting(myRoast))
    }
  }

  return(
    <div className='flex flex-col items-center gap-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='font-bold'>로스팅 단계 선택</p>
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
                onClick={()=>handleRoastSelect(num,isSelected,setIsSelected)}
              />
              <p>{label}</p>
            </div>
          );
        })
      }

      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleRoastSubmit} />
    </div>
  )
}