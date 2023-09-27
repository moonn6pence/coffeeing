import React from 'react';
import zeroBean from 'assets/detail/zero_bean.png';
import halfBean from 'assets/detail/half_bean.png';
import oneBean from 'assets/detail/one_bean.png';

type BeanRatingProps = {
  acidity: number;
  roast: number;
  body: number;
};

export const BeanRating = (props: BeanRatingProps) => {
  const { acidity, roast, body } = props;

  const beanCharac = [
    { name: '로스팅', value: roast },
    { name: '바디감', value: body },
    { name: '산 미', value: acidity },
  ];

  const commonSize = 'w-12 h-12';
  // 콩 이미지를 저장할 배열
  const beansRating = (rate: number) => {
    const beans = [];
    // 이미지 저장 기능
    for (let i = 0; i < 5; i++) {
      // 현재 인덱스 i와 평점을 비교
      if (i < Math.floor(rate)) {
        // 현재 인덱스가 평점 미만이면 oneBean
        beans.push(
          <img key={i} src={oneBean} alt="한 콩" className={commonSize} />,
        );
      } else if (i === Math.floor(rate) && rate % 1 !== 0) {
        // 현재 인덱스가 평점과 같고, 평점이 소수일 때 halfBean
        beans.push(
          <img key={i} src={halfBean} alt="반 콩" className={commonSize} />,
        );
      } else {
        beans.push(
          <img key={i} src={zeroBean} alt="빈 콩" className={commonSize} />,
        );
      }
    }
    return beans;
  };

  return (
    <div className="flex flex-col space-y-3">
      {beanCharac.map((charac) => (
        <div className="flex w-full justify-between" key={charac.name}>
          <span className="text-xl font-bold my-auto">{charac.name}</span>
          <div className="flex space-x-3">{beansRating(charac.value)}</div>
        </div>
      ))}
    </div>
  );
};
