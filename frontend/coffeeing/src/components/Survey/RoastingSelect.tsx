import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import lightRoast from '../../assets/survey/lightRoast.png';
import mediumRoast from '../../assets/survey/mediumRoast.png';
import darkRoast from '../../assets/survey/darkRoast.png';
import unknownRoast from '../../assets/survey/unknownRoast.png';
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveRoasting } from 'store/surveySlice';
import { NextButton } from './SurveyButton';
import { RoastingTooltip } from 'components/Tooltip/survey';
import { RadioGroupSingle } from './RadioGroup';
import { SURVEY_ROAST_ITEMS } from 'util/constants';
export const RoastingSelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const dispatch = useDispatch<AppDispatch>();
  const [selectedRoast, setSelectedRoast] = useState(0)
  useEffect(()=>{
    console.log(selectedRoast)
  },[selectedRoast])

  // 다음 버튼 클릭 시
  const handleRoastSubmit = () => {
    // 선택 안 했을 때
    if (selectedRoast === -1) {
      alert('선호하는 로스팅 단계를 하나 선택해주세요');
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
            className={` botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-half-light`}
          ></span>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className="flex flex-row gap-10">
        <RadioGroupSingle 
          selectedItem={selectedRoast}
          setSelectedItem={setSelectedRoast}
          itemList={SURVEY_ROAST_ITEMS}
          pageNum={1}
        />
      </div>
      {/* 버튼 */}
      <NextButton handleClick={handleRoastSubmit} label='다음' />
    </div>
  );
};
