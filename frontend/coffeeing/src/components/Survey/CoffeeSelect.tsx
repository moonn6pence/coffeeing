import React,{Dispatch, SetStateAction, useState} from 'react';
import { useDispatch } from 'react-redux';
import coffeeBean from '../../assets/survey/coffee/coffeeBean.png'
import coffeeCapsule from '../../assets/survey/coffee/coffeeCapsule.png'
import { AppDispatch  } from 'store/store';
import { addCurrentPage, setTotalPage } from 'store/surveySlice';
import { NextButton } from './NextButton';

export const CoffeeSelect = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [selectedBean, setSelectedBean]= useState(false)
  const [selectedCapsule, setSelectedCapsule]= useState(false)
  const [myCoffee, setMyCoffee] = useState(-1);
  // 데이터
  const data = [
    {src:coffeeBean, label:'원두',isSelected:selectedBean, setIsSelected:setSelectedBean,num:0},
    {src:coffeeCapsule, label:'캡슐',isSelected:selectedCapsule, setIsSelected:setSelectedCapsule,num:1}
  ]
  // 로스팅 단계 이미지 클릭 시
  const handleCoffeeSelect = (num:number,isSelected:boolean,setIsSelected:Dispatch<SetStateAction<boolean>>)=>{
    if(myCoffee===-1){
      setMyCoffee(num)
      setIsSelected(!isSelected)
    } else if (myCoffee===num) {
      setMyCoffee(-1)
      setIsSelected(!isSelected)
    } else {
      alert('하나만 선택해주세요')
    }
  }
  // 다음 버튼 클릭 시
  const handleCoffeeSubmit = ()=>{
    if (myCoffee===-1){
      alert('추천받고 싶은 대상을 하나 선택해주세요.')
    } 
    // 원두 일 때
    else if (myCoffee===0) {
      dispatch(addCurrentPage())
      dispatch(setTotalPage(4))
    } else {
      dispatch(addCurrentPage())
      dispatch(setTotalPage(5))
    }
  }

  return(
    <div className='flex flex-col items-center gap-5 mt-10'>
      {/* 설문 상단 */}
      <p className='text-2xl font-bold'>어떤 종류의 커피를 추천해 드릴까요? </p>
      {/* 설문 사진 */}
      <div className='flex flex-row gap-10'>
      {
        data.map((item)=>{
          const { src, label, isSelected, setIsSelected, num } = item;
          return(
            <div className={`w-96 h-80 flex flex-col items-center ${isSelected ? 'bg-select-img' : ''} rounded-xl`} key={num}>
            <img
              className={`w-80 h-72  origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
              src={src}
              onClick={()=>handleCoffeeSelect(num,isSelected,setIsSelected)}
            />
            <p className='text-xl'>{label}</p>
          </div>
          )
        })
      }
      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleCoffeeSubmit}/>
    </div>
  )
}