import React, {useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveRoasting } from 'store/surveySlice';
import { NextButton } from './SurveyButton';
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_ROAST_ITEMS } from 'util/constants';
import { BackButton } from './SurveyButton';
import { Toast } from 'components/Toast';
export const RoastingSelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const dispatch = useDispatch<AppDispatch>();
  const [selectedRoast, setSelectedRoast] = useState(-2)
  // useEffect(()=>{
  //   console.log(selectedRoast)
  // },[selectedRoast])

  // 다음 버튼 클릭 시
  const handleRoastSubmit = () => {
    // 선택 안 했을 때
    if (selectedRoast === -2) {
      Toast.fire('선호하는 로스팅 단계를 하나 선택해주세요','','warning')
    }
    // 선택 했을 때 - 다음 페이지로 & 로스팅 정보 저장
    else {
      dispatch(addCurrentPage());
      dispatch(saveRoasting(selectedRoast));
    }
  };

  return (
    <div className="flex flex-col items-center gap-10 mt-10">
      {/* 설문 상단 */}
      <div className="flex flex-col items-center gap-2">
        <p>
          {survey.currentPage}/{survey.totalPage}
        </p>
        <div className="flex flex-row">
          <p className=" text-2xl font-bold">
            선호하는 로스팅 단계를 선택해주세요
          </p>
        </div>
        <p className="flex w-560px h-2.5 rounded-lg bg-process-bar">
          <span
            className={`botton-0 left-0 ${survey.totalPage === 4 ? (selectedRoast !== -2 ? 'w-1/4 transition-width duration-500 ease-in-out' : 'w-0') : (selectedRoast !== -2 ? 'w-1/5 transition-width duration-500 ease-in-out' : 'w-0')} h-2.5 rounded-lg bg-half-light`}
          ></span>

        </p>
      </div>
      {/* 설문 사진 */}
      <div >
        <RadioGroupSingle 
          selectedItem={selectedRoast}
          setSelectedItem={setSelectedRoast}
          itemList={SURVEY_ROAST_ITEMS}
          pageNum={1}
        />
      </div>
      {/* 버튼 */}
      <div className='flex flex-row gap-10 mb-20'>
        <BackButton/>
        <NextButton handleClick={handleRoastSubmit} label='다음' />
      </div>
        
    </div>
  );
};
