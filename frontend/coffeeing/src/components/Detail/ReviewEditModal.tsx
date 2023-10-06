import React, { FormEvent, useState } from 'react';
import { StarRating } from './StarRating';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import quitModal from 'assets/quit-modal-icon.svg';

type ReviewEditProps = {
  content: string;
  score: number;
  beans?: string;
  reviewId?: number;
  handleModal: () => void;
};

export const ReviewEditModal = (props: ReviewEditProps) => {
  const { content, score, beans, reviewId, handleModal } = props;
  // 별점 상태
  const [rating, setRating] = useState(score);
  // 별점 상태 업데이트
  const handleRatingChange = (newRating: number) => {
    setRating(newRating);
  };

  // 리뷰 내용 state
  const [description, setDescription] = useState(content);
  // 리뷰 내용 변경상태 받기
  const onChangeDescription = (e: FormEvent<HTMLInputElement>) => {
    setDescription(e.currentTarget.value);
  };

  const submitReview = (event: FormEvent) => {
    event.preventDefault();
    if (!rating) {
      alert('별점을 입력해주세요');
    } else if (!description) {
      alert('내용을 입력해주세요');
    } else {
      privateRequest
        .put(`${API_URL}/product/${beans}/review/${reviewId}`, {
          content: description,
          score: rating,
        })
        .then(() => {
          handleModal();
          window.location.reload();
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div className="z-10 bg-black/30 w-screen h-screen fixed left-0 top-0 flex justify-center items-center">
      <div className="w-560px h-[560px] bg-light rounded-lg p-6">
        <form
          onSubmit={submitReview}
          className="flex flex-col justify-between h-full"
        >
          <div className="flex justify-between">
            <div className="flex">
              <span className="font-bold text-2xl mr-2 my-auto">내 리뷰</span>
              <label>
                <StarRating
                  totalStars={5}
                  rating={rating}
                  onRatingChange={handleRatingChange}
                />
              </label>
            </div>
            <button onClick={handleModal}>
              <img src={quitModal} />
            </button>
          </div>
          <div className="bg-white rounded-lg my-6 h-full w-full">
            <label className="label" id="description-label">
              <input
                type="text"
                maxLength={100}
                onChange={onChangeDescription}
                placeholder={content}
                className="bg-transparent text-base focus:outline-none mt-6 ml-6 w-full break-all"
              />
            </label>
          </div>
          <button className="bg-[#1F170B] text-white rounded-3xl py-3 px-22px w-fit self-end">
            수정하기
          </button>
        </form>
      </div>
    </div>
  );
};
