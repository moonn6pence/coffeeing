import React from 'react';
import star from 'assets/detail/star.svg';
import { ReviewProps } from './ReviewCard';
import editImg from 'assets/edit.svg';
import deleteImg from 'assets/delete.svg';

type MyReviewProps = {
  memberReview: ReviewProps;
};

export const MyReview = (props: MyReviewProps) => {
  const { memberReview } = props;

  const starIcons = [];
  // memberReview.score로 연결해줘야함
  for (let i = 0; i < 4; i++) {
    starIcons.push(<img src={star} alt="별" key={i} className="w-9 h-9" />);
  }
  return (
    <div className="w-300 h-200px bg-light rounded-lg p-6 flex flex-col">
      <div className="flex justify-between">
        <div className="flex space-x-1">{starIcons}</div>
        <div>
          <button>
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
