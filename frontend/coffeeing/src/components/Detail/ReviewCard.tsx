import { StarIcons } from 'components/StarIcons';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import noProfile from 'assets/noprofile.png';

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

  return (
    <div className="w-1/3 p-3">
      <div className="flex mb-6">
        <img
          src={profileImageUrl || noProfile}
          alt="사진"
          className="w-9 h-9 rounded-full cursor-pointer"
          onClick={() => {
            navigate(`/member/${memberId}`);
          }}
        />
        <div className="ml-3">
          <p className="text-sm">{nickname}</p>
          <StarIcons score={score} />
        </div>
      </div>
      <div className="bg-light rounded-lg p-3 h-104px">
        <p className="text-sm">{content}</p>
      </div>
    </div>
  );
};
