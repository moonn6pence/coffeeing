import water from '../../assets/survey/body/water.png';
import icedTea from '../../assets/survey/body/iced_tea_1.png';
import smoothie from '../../assets/survey/body/smoothie.png';
import milk from '../../assets/survey/body/milk.png';
import unknownBody from '../../assets/survey/body/unknownBody.png';

import React, { Dispatch, SetStateAction, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';
import { NextButton, BackButton } from './SurveyButton';
import { addCurrentPage } from 'store/surveySlice';
import { saveBody } from 'store/surveySlice';

export const BodySelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const dispatch = useDispatch<AppDispatch>();
  const [selectedWater, setSelectedWater] = useState(false);
  const [selectedIcedTea, setSelectedIcedTea] = useState(false);
  const [selectedMilk, setSelectedMilk] = useState(false);
  const [selectedUnknown, setSelectedUnknown] = useState(false);
  const [myBody, setMyBody] = useState(-1);

  const data = [
    {
      src: water,
      label: '물 같은 가벼운 느낌',
      isSelected: selectedWater,
      setIsSelected: setSelectedWater,
      num: 0.3,
    },
    {
      src: milk,
      label: '우유 같은 묵직한 느낌',
      isSelected: selectedMilk,
      setIsSelected: setSelectedMilk,
      num: 0.9,
    },
    {
      src: smoothie,
      label: '스무디 같은 꾸덕꾸덕한 느낌 ',
      isSelected: selectedIcedTea,
      setIsSelected: setSelectedIcedTea,
      num: 0.6,
    },
    {
      src: unknownBody,
      label: '잘 모르겠어요',
      isSelected: selectedUnknown,
      setIsSelected: setSelectedUnknown,
      num: 0,
    },
  ];

  const handleBodySelect = (
    num: number,
    isSelected: boolean,
    setIsSelected: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (myBody === -1) {
      setMyBody(num);
      setIsSelected(!isSelected);
    } else if (myBody === num) {
      setMyBody(-1);
      setIsSelected(!isSelected);
    } else {
      alert('하나만 선택해주세요');
    }
  };
  const handleBodyClick = () => {
    if (myBody === -1) {
      alert('선호하는 바디감을 하나만 선택해주세요');
    } else {
      dispatch(addCurrentPage());
      dispatch(saveBody(myBody));
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
      <div className="flex flex-row gap-10">
        {data.map((item) => {
          const { src, label, isSelected, setIsSelected, num } = item;
          return (
            <div
              className={`w-64 h-60 flex flex-col items-center ${
                isSelected ? 'bg-select-img' : ''
              } rounded-xl`}
              key={num}
            >
              <img
                className={`w-52 h-52 origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
                src={src}
                onClick={() => handleBodySelect(num, isSelected, setIsSelected)}
              />
              <p className="mt-2">{label}</p>
            </div>
          );
        })}
      </div>
      {/* 버튼 */}
      <div className="flex gap-10">
        <BackButton />
        <NextButton handleClick={handleBodyClick} label='다음' />
      </div>
    </div>
  );
};
