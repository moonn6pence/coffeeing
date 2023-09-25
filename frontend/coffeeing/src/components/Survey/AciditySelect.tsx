import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import noAcid from '../../assets/survey/acidity/noAcid.png';
import lowAcid from '../../assets/survey/acidity/lowAcid.png';
import mediumAcid from '../../assets/survey/acidity/mediumAcid.png';
import highAcid from '../../assets/survey/acidity/highAcid.png';
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveAcidity } from 'store/surveySlice';
import { NextButton, BackButton } from './SurveyButton';

export const AciditySelect = () => {
  const survey = useSelector((state: RootState) => state.survey);
  const [widthClass, setWidthClass] = useState('');
  const dispatch = useDispatch<AppDispatch>();
  const [selectedNoAcid, setSelectedNoAcid] = useState(false);
  const [selectedLowAcid, setSelectedLowAcid] = useState(false);
  const [selectedMediumAcid, setSelectedMediumAcid] = useState(false);
  const [selectedHighAcid, setSelectedHighAcid] = useState(false);
  const [myAcidity, setMyAcidity] = useState(-1);

  useEffect(() => {
    setWidthClass(`w-${survey.currentPage}/${survey.totalPage}`);
  }, []);

  // 데이터
  const data = [
    {
      src: noAcid,
      label: '없음',
      isSelected: selectedNoAcid,
      setIsSelected: setSelectedNoAcid,
      num: 0.25,
    },
    {
      src: lowAcid,
      label: '낮음',
      isSelected: selectedLowAcid,
      setIsSelected: setSelectedLowAcid,
      num: 0.5,
    },
    {
      src: mediumAcid,
      label: '중간',
      isSelected: selectedMediumAcid,
      setIsSelected: setSelectedMediumAcid,
      num: 0.75,
    },
    {
      src: highAcid,
      label: '높음',
      isSelected: selectedHighAcid,
      setIsSelected: setSelectedHighAcid,
      num: 1,
    },
  ];
  // 산미 단계 이미지 클릭 시
  const handleAciditySelect = (
    num: number,
    isSelected: boolean,
    setIsSelected: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (myAcidity === -1) {
      setMyAcidity(num);
      setIsSelected(!isSelected);
    } else if (myAcidity === num) {
      setMyAcidity(-1);
      setIsSelected(!isSelected);
    } else {
      alert('하나만 선택해주세요');
    }
  };
  // 다음 버튼 클릭 시
  const handleAciditySubmit = () => {
    // 선택 안 했을 때
    if (myAcidity === -1) {
      alert('선호하는 산미 단계를 하나 선택해주세요');
    }
    // 선택 했을 때 - 다음 페이지로 & 산미 정보 저장
    else {
      dispatch(addCurrentPage());
      dispatch(saveAcidity(myAcidity));
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
                onClick={() =>
                  handleAciditySelect(num, isSelected, setIsSelected)
                }
              />
              <p>{label}</p>
            </div>
          );
        })}
      </div>
      {/* 버튼 */}
      <div className="flex gap-10">
        <BackButton />
        <NextButton handleClick={handleAciditySubmit} label='다음' />
      </div>
    </div>
  );
};
