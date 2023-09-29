import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveAcidity } from 'store/surveySlice';
import { NextButton, BackButton } from './SurveyButton';
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_ACID_ITEMS } from 'util/constants';
export const AciditySelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const [widthClass, setWidthClass] = useState('');
  const dispatch = useDispatch<AppDispatch>();
  const [selectedAcidity, setSelectedAcidity] = useState(-1);

  // 다음 버튼 클릭 시
  const handleAciditySubmit = () => {
    // 선택 안 했을 때
    if (selectedAcidity === -1) {
      alert('선호하는 산미 단계를 하나 선택해주세요');
    }
    // 선택 했을 때 - 다음 페이지로 & 산미 정보 저장
    else {
      dispatch(addCurrentPage());
      dispatch(saveAcidity(selectedAcidity));
    }
  };
  return (
    <div className="flex flex-col items-center gap-10 mt-10">
      {/* 설문 상단 */}
      <div className="flex flex-col items-center gap-2">
        <p>
          {survey.currentPage}/{survey.totalPage}
        </p>
        <p className="text-2xl font-bold">선호하는 산미 단계를 선택해주세요</p>
        <p className="flex w-560px h-2.5 rounded-lg bg-process-bar">
          <span
            className={` botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-light-roasting`}
          ></span>
        </p>
      </div>
      {/* 설문 사진 */}
      <RadioGroupSingle 
        selectedItem={selectedAcidity}
        setSelectedItem={setSelectedAcidity}
        itemList={SURVEY_ACID_ITEMS}
        pageNum={2}
      />
      {/* 버튼 */}
      <div className="flex gap-10">
        <BackButton />
        <NextButton handleClick={handleAciditySubmit} label='다음' />
      </div>
    </div>
  );
};
