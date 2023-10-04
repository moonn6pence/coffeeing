import React, {  useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {  RootState } from 'store/store';
import { NextButton, BackButton } from './SurveyButton';
import { addCurrentPage } from 'store/surveySlice';
import { saveBody } from 'store/surveySlice';
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_BODY_ITEMS } from 'util/constants';
import { Toast } from 'components/Toast';
export const BodySelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const dispatch = useDispatch()
  const [selectedBody, setSelectedBody] = useState(-2);

  const handleBodyClick = () => {
    if (selectedBody === -2) {
      Toast.fire('선호하는 바디감을 하나 선택해주세요.','','warning')
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
        <p className="flex w-560px h-2.5 rounded-lg bg-process-bar">
          <span
            className={`botton-0 left-0 ${survey.totalPage === 4 ? (selectedBody !== -2 ? 'w-3/4 transition-width duration-500 ease-in-out' : 'w-2/4') : (selectedBody !== -2 ? 'w-3/5 transition-width duration-500 ease-in-out' : 'w-2/5')} h-2.5 rounded-lg bg-half-light`}
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
