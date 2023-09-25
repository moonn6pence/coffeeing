import React from 'react';
import { ReviewProps } from './ReviewCard';
import editImg from 'assets/edit.svg';
import deleteImg from 'assets/delete.svg';
import { StarIcons } from 'components/StarIcons';

type MyReviewProps = {
  memberReview: ReviewProps;
  handleModal: () => void;
};

export const MyReview = (props: MyReviewProps) => {
  const { memberReview, handleModal } = props;

  return (
    <div className="w-300 h-200px bg-light rounded-lg p-6 flex flex-col">
      <div className="flex justify-between">
        <StarIcons score={3} size="big" />
        <div>
          <button onClick={handleModal}>
            <img className="w-9 h-9" src={editImg} alt="수정" />
          </button>
          <button>
            <img className="w-9 h-9" src={deleteImg} alt="삭제" />
          </button>
        </div>
      </div>
      <p className="text-base h-10 w-full mt-3">내용</p>
    </div>
  );
};
