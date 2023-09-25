import React, { useState } from 'react';
import starOn from 'assets/detail/star_on.svg';
import starOff from 'assets/detail/star_off.svg';

type StarRatingProps = {
  totalStars: number;
  rating: number; // 별점 상태
  onRatingChange: (newRating: number) => void;
};

export const StarRating: React.FC<StarRatingProps> = ({
  totalStars,
  rating,
  onRatingChange,
}) => {
  const [hoveredRating, setHoveredRating] = useState(0);

  // 마우스 호버됐을 때
  const handleMouseEnter = (star: number) => {
    setHoveredRating(star);
  };

  const handleMouseLeave = () => {
    setHoveredRating(0);
  };

  const onClick = (star: number) => {
    onRatingChange(star);
  };

  return (
    <div className="flex space-x-1">
      {[...Array(totalStars)].map((star, index) => {
        const num = index + 1;
        return (
          <img
            className="w-9 h-9"
            src={num <= (hoveredRating || rating) ? starOn : starOff}
            key={num}
            onMouseEnter={() => handleMouseEnter(num)}
            onMouseLeave={handleMouseLeave}
            onClick={() => onClick(num)}
          />
        );
      })}
    </div>
  );
};
