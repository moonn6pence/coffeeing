import React from 'react';
import star from 'assets/detail/star.svg';

type StarIconsProps = {
  score: number;
  size?: string;
};

export const StarIcons = (props: StarIconsProps) => {
  const { score, size = 'small' } = props;
  const imgSize = size === 'big' ? 'w-9 h-9 space-x-4' : 'w-3.5 h-3.5';
  const starIcons = [];
  for (let i = 0; i < score; i++) {
    starIcons.push(<img src={star} alt="별" key={i} className={imgSize} />);
  }

  return <div className="flex">{starIcons}</div>;
};
