import React from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';
import { Pagination } from 'components/Pagination';
import { CapsuleCard } from 'components/CapsuleCard';

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
    memberReview: { content: 'string', id: 0, nickname: 'string', score: 0 },
    name: '아르페지오',
    roast: 2.5,
  };

  // 더미데이터(2페이지)
  const reviews = [
    { content: '1', id: 0, nickname: '김씨', score: 3 },
    { content: '2', id: 1, nickname: '이씨', score: 4 },
    { content: '3', id: 2, nickname: '박씨', score: 3 },
    { content: '4', id: 3, nickname: '용씨', score: 3 },
    { content: '5', id: 4, nickname: '용씨', score: 3 },
    { content: '6', id: 5, nickname: '용씨', score: 3 },
    { content: '7', id: 6, nickname: '용씨', score: 3 },
  ];

  const similarList = [
    { name: '아르페지오', brand: '네스프레소', capsule_id: 1, imgLink: '/' },
    { name: '니카라과', brand: '네스프레소', capsule_id: 2, imgLink: '/' },
    { name: '코지', brand: '네스프레소', capsule_id: 3, imgLink: '/' },
    { name: '인도네시아', brand: '네스프레소', capsule_id: 4, imgLink: '/' },
  ];

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
      <div className="w-fit mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">리뷰 남기기</p>
        <ReviewForm />
      </div>
      <div className="w-fit mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">
          평균 평점 {capsule.averageScore}{' '}
          <span className="text-[#BE9E8B]">/ 5.0</span>
        </p>
        <div className="flex space-x-6">
          {/* 나중에 review 받아온 걸로 연결해줄 예정 */}
          <Pagination limit={6} contentList={reviews} />
        </div>
      </div>
      <div className="w-fit mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">비슷한</p>
        <div className="flex w-300 justify-between">
          {similarList.map((item, index) => (
            <CapsuleCard
              brand={item.brand}
              name={item.name}
              capsule_id={item.capsule_id}
              imgLink={item.imgLink}
              key={index}
            />
          ))}
        </div>
      </div>
    </div>
  );
};
