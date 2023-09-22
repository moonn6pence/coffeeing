import React, { useEffect, useState } from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';
import { Pagination } from 'components/Pagination';
import { CapsuleCard } from 'components/CapsuleCard';
import { publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useLocation } from 'react-router-dom';

export const DetailPage = () => {
  const location = useLocation();
  const id = location.state.id;

  // 상품 정보 불러오기
  useEffect(() => {
    publicRequest
      .get(`${API_URL}/product/capsule/${id}`)
      .then((res) => {
        setCapsule(res.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const [capsule, setCapsule] = useState({
    acidity: 0,
    aroma: 'string',
    averageScore: 0,
    body: 0,
    brand: 'string',
    description: 'string',
    id: 0,
    imageUrl: 'string',
    isBookmarked: true,
    isReviewed: true,
    memberReview: {
      content: 'string',
      memberId: 0,
      nickname: 'string',
      profileImageUrl: 'string',
      reviewId: 0,
      score: 0,
    },
    nameKr: 'string',
    roast: 0,
  });

  // 리뷰들 불러오기
  useEffect(() => {
    publicRequest
      .get(`${API_URL}/product/capsule/${id}/review`, { params: { page: 0 } })
      .then((res) => {
        // console.log(res.data.data.reviews);
        setReviews(res.data.data.reviews);
      });
  }, [capsule]);

  const [reviews, setReviews] = useState([
    {
      content: '',
      memberId: 0,
      nickname: '',
      profileImageUrl: '',
      reviewId: 0,
      score: 0,
    },
  ]);

  const similarList = [
    { name: '아르페지오', brand: '네스프레소', capsule_id: 1, imgLink: '/' },
    { name: '니카라과', brand: '네스프레소', capsule_id: 2, imgLink: '/' },
    { name: '코지', brand: '네스프레소', capsule_id: 3, imgLink: '/' },
    { name: '인도네시아', brand: '네스프레소', capsule_id: 4, imgLink: '/' },
  ];

  const beanDetail = {
    roast: capsule.roast * 5,
    body: capsule.body * 5,
    acidity: capsule.acidity * 5,
    brand: capsule.brand,
    name: capsule.nameKr,
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
          평균 평점 {capsule.averageScore.toFixed(1)}{' '}
          <span className="text-[#BE9E8B]">/ 5.0</span>
        </p>
        <div className="flex space-x-6">
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
