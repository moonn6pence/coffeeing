import React,{Dispatch, SetStateAction, useState}  from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { saveMachineType } from 'store/surveySlice'
import { useNavigate } from 'react-router-dom'
import { NextButton } from './SurveyButton'
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_MACHINE_ITEMS } from 'util/constants';
export const MachineSelect = () => {
  const navigate = useNavigate();
  const survey = useSelector((state:RootState)=>state.survey)
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const [selectedMachine, setSelectedMachine] = useState(-1)
  const dispatch = useDispatch<AppDispatch>();

  const handleSurveySubmit = ()=>{
    dispatch(saveMachineType(selectedMachine))  
    navigate('/recommend-result')
  }

  return(
    <div className='flex flex-col items-center gap-5 mt-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='text-2xl font-bold'>사용 중인 커피 머신을 선택해주세요</p>
        <p className='relative w-560px h-2.5 rounded-lg bg-process-bar'>
          <p className={`absolute botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-light-roasting`}></p>
        </p>
      </div>
      {/* 설문 사진 */}
      <RadioGroupSingle
        selectedItem={selectedMachine}
        setSelectedItem={setSelectedMachine}
        itemList={SURVEY_MACHINE_ITEMS}
        pageNum={5}
      />
      {/* 버튼 */}
      <NextButton handleClick={handleSurveySubmit} label='제출하기'/>
    </div>
  )
}