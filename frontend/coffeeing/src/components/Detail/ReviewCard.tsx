import React from 'react';
import star from 'assets/detail/star.svg';
import { useNavigate } from 'react-router-dom';

export type ReviewProps = {
  content: string;
  memberId: number;
  nickname: string;
  profileImageUrl: string;
  reviewId: number;
  score: number;
};

type MemberReviewProps = {
  memberReview: ReviewProps;
};

export const ReviewCard = ({ memberReview }: MemberReviewProps) => {
  const { content, memberId, nickname, score, profileImageUrl } = memberReview;
  const navigate = useNavigate();

  const starIcons = [];
  for (let i = 0; i < score; i++) {
    starIcons.push(<img src={star} alt="별" key={i} className="w-3.5 h-3.5" />);
  }

  return (
    <div className="w-96 p-3">
      <div className="flex mb-6">
        <img
          src={profileImageUrl}
          alt="사진"
          onClick={() => {
            navigate(`/member/${memberId}`);
          }}
        />
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
