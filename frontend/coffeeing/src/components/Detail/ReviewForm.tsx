import React, { ChangeEvent, useState } from 'react';
import { Form } from 'react-router-dom';

export const ReviewForm = () => {
  // 리뷰 내용 state
  const [description, setDescription] = useState('');

  // 리뷰 내용 변경상태 받기
  const onChangeDescription = (e: ChangeEvent<HTMLInputElement>) => {
    setDescription(e.target.value);
  };

  return (
    <form action="">
      <label className="label" id="description-label">
        <input
          type="text"
          name=""
          id=""
          maxLength={100}
          onChange={onChangeDescription}
          placeholder={'상태 메세지를 입력해주세요'}
        />
      </label>
    </form>
  );
};
