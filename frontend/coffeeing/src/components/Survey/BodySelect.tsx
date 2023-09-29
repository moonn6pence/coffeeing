import React, { Dispatch, SetStateAction, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { NextButton, BackButton } from './SurveyButton';
import { addCurrentPage } from 'store/surveySlice';
import { saveBody } from 'store/surveySlice';
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_BODY_ITEMS } from 'util/constants';
export const BodySelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const dispatch = useDispatch()
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const [selectedBody, setSelectedBody] = useState(-2);

  const handleBodyClick = () => {
    if (selectedBody === -1) {
      alert('선호하는 바디감을 하나만 선택해주세요');
    } else {
      dispatch(addCurrentPage());
      dispatch(saveBody(selectedBody));
    }
  };

  return (
    <div className="flex flex-col items-center gap-10 mt-10">
      {/* 설문 상단 */}
      <div className="flex flex-col items-center gap-2">
        <p>
          {survey.currentPage}/{survey.totalPage}
        </p>
        <p className="text-2xl font-bold">선호하는 바디감을 선택해주세요</p>
        <p className="relative w-560px h-2.5 rounded-lg bg-process-bar">
          <span
            className={`absolute botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-light-roasting`}
          ></span>
        </p>
      </div>
      {/* 설문 사진 */}
      <RadioGroupSingle
        selectedItem={selectedBody}
        setSelectedItem={setSelectedBody}
        itemList={SURVEY_BODY_ITEMS}
        pageNum={3}
        />
      {/* 버튼 */}
      <div className="flex gap-10">
        <BackButton />
        <NextButton handleClick={handleBodyClick} label='다음' />
      </div>
    </div>
  );
};
