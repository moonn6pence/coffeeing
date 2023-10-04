import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { addCurrentPage, setTotalPage, saveIsCapsule } from 'store/surveySlice';
import { NextButton } from './SurveyButton';
import { RadioGroupSingle } from './RadioGroup';
import { COFFEE_ITEMS } from 'util/constants';
import { Toast } from 'components/Toast';

export const CoffeeSelect = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [selectedCoffee, setSelectedCoffee] = useState('')

  // 다음 버튼 클릭 시
  const handleCoffeeSubmit = () => {
    if (selectedCoffee === '') {
      Toast.fire('추천받고 싶은 대상을 하나 <br>선택해주세요.','','warning')
    }
    // 원두 일 때
    else if (selectedCoffee === 'bean') {
      dispatch(addCurrentPage());
      dispatch(setTotalPage(4));
    } else {
      dispatch(saveIsCapsule());
      dispatch(addCurrentPage());
      dispatch(setTotalPage(5));
    }
  };

  return (
    <div className="flex flex-col items-center gap-5 mt-10">
      {/* 설문 상단 */}
      <p className="text-2xl font-bold">어떤 종류의 커피를 추천해 드릴까요? </p>
      {/* 설문 사진 */}
        <RadioGroupSingle
          selectedItem={selectedCoffee}
          setSelectedItem={setSelectedCoffee}
          itemList={COFFEE_ITEMS}
          pageNum={0}
          />
      {/* 버튼 */}
      <NextButton handleClick={handleCoffeeSubmit} label='다음' />
    </div>
  );
};
