import React from 'react';
import zeroBean from 'assets/detail/zero_bean.png';
import halfBean from 'assets/detail/half_bean.png';
import oneBean from 'assets/detail/one_bean.png';

export const BeanRating = ({ rate }: { rate: number }) => {
  const commonSize = 'w-12 h-12';
  // 콩 이미지를 저장할 배열
  const beansRating = [];

  // 이미지 저장 기능
  for (let i = 0; i < 5; i++) {
    // 현재 인덱스 i와 평점을 비교
    if (i < Math.floor(rate)) {
      // 현재 인덱스가 평점 미만이면 oneBean
      beansRating.push(
        <img key={i} src={oneBean} alt="한 콩" className={commonSize} />,
      );
    } else if (i === Math.floor(rate) && rate % 1 !== 0) {
      // 현재 인덱스가 평점과 같고, 평점이 소수일 때 halfBean
      beansRating.push(
        <img key={i} src={halfBean} alt="반 콩" className={commonSize} />,
      );
    } else {
      beansRating.push(
        <img key={i} src={zeroBean} alt="빈 콩" className={commonSize} />,
      );
    }
  }

  return <div className="flex space-x-3">{beansRating}</div>;
};
