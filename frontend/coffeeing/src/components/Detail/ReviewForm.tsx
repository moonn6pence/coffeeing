import React, { FormEvent, useState } from 'react';
import { StarRating } from './StarRating';

export const ReviewForm = () => {
  // 리뷰 내용 state
  const [description, setDescription] = useState('');
  // 리뷰 내용 변경상태 받기
  const onChangeDescription = (e: FormEvent<HTMLInputElement>) => {
    setDescription(e.currentTarget.value);
  };

  // 별점 상태
  const [rating, setRating] = useState(0);
  // 별점 상태 업데이트
  const handleRatingChange = (newRating: number) => {
    setRating(newRating);
  };

  const submitReview = () => {
    // 별점, 내용 담아서 보내는 거 연결할 예정
    if (!rating) {
      alert('별점을 입력해주세요');
    } else if (!description) {
      alert('내용을 입력해주세요');
    } else {
      console.log('제출');
    }
  };

  return (
    <form
      onSubmit={submitReview}
      className="w-300 h-200px bg-light rounded-lg p-6 flex flex-col justify-between"
    >
      <label>
        <StarRating
          totalStars={5}
          rating={rating}
          onRatingChange={handleRatingChange}
        />
      </label>
      <label className="label" id="description-label">
        <input
          type="text"
          maxLength={100}
          onChange={onChangeDescription}
          placeholder={'내용을 입력해주세요'}
          className="bg-transparent text-base mt-3 h-10 w-full"
        />
      </label>
      <button className="bg-[#1F170B] text-white rounded-3xl py-3 px-22px w-fit self-end">
        작성하기
      </button>
    </form>
  );
};
