import React from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';

export const DetailPage = () => {
  // 더미 데이터, 나중에 받아온 데이터로 연결할 예정
  const capsule = {
    acidity: 2,
    aroma: '코코아',
    averageScore: 0,
    body: 3.5,
    brand: '네스프레소',
    description:
      '짧게 다크로스팅한 브라질산 원두가 강한 로스팅향과 균형감을 주고 다크로스팅한 코스타리카 원두가 쌉싸름한 코코아향에 무게감을 더해주는 커피입니다. 정교한 그라인딩으로 벨벳처럼 부드러워 거부할 수 없는 크리미한 질감을 표현했습니다.',
    id: 0,
    imageUrl: 'string',
    isBookmarked: true,
    isReviewed: true,
    memberReview: {
      content: 'string',
      id: 0,
      nickname: 'string',
      score: 0,
    },
    name: '아르페지오',
    roast: 2.5,
  };

  const beanDetail = {
    roast: capsule.roast,
    body: capsule.body,
    acidity: capsule.acidity,
    brand: capsule.brand,
    name: capsule.name,
    isBookmarked: capsule.isBookmarked,
    imageUrl: capsule.imageUrl,
    description: capsule.description,
    aroma: capsule.aroma,
  };

  return (
    <div>
      <BeanDetailBody {...beanDetail} />
      <p className='text-2xl font-bold'>리뷰 남기기</p>
      <ReviewForm />
    </div>
  );
};
