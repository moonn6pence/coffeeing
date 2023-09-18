import React from 'react';
import star from 'assets/detail/star.svg';
import profile from 'assets/profile.svg';

export type ReviewProps = {
  content: string;
  id: number;
  nickname: string;
  score: number;
};

type MemberReviewProps = {
  memberReview: ReviewProps;
};

export const ReviewCard = ({ memberReview }: MemberReviewProps) => {
  const { content, id, nickname, score } = memberReview;

  const starIcons = [];
  for (let i = 0; i < score; i++) {
    starIcons.push(<img src={star} alt="별" key={i} className="w-3.5 h-3.5" />);
  }

  return (
    <div className="w-96 p-3">
      <div className="flex mb-6">
        {/* 프로필 사진 연결해줘야함 */}
        <img src={profile} alt="사진" />
        <div className="ml-3">
          <p className="text-sm">{nickname}</p>
          <div className="flex">{starIcons}</div>
        </div>
      </div>
      <div className="bg-light rounded-lg p-3 h-104px">
        <p className="text-sm">{content}</p>
      </div>
    </div>
  );
};
