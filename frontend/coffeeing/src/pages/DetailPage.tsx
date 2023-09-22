import React, { useEffect, useState } from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';
import { Pagination } from 'components/Pagination';
import { CapsuleCard } from 'components/CapsuleCard';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useParams } from 'react-router-dom';

export const DetailPage = () => {
  const { beans, id } = useParams();

  // 상품 정보 불러오기
  useEffect(() => {
    publicRequest
      .get(`${API_URL}/product/${beans}/${id}`)
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
    privateRequest
      .get(`${API_URL}/product/${beans}/${id}/review`, {
        params: { page: 0 },
      })
      .then((res) => {
        // console.log(res.data.data.reviews);
        setReviews(res.data.data.reviews);
      })
      .catch((error) => {
        console.log(error);
      });
    privateRequest
      .get(`${API_URL}/product/${beans}/${id}/similar`)
      .then((res) => {
        setSimilarList(res.data.data.products);
      })
      .catch((error) => {
        console.log(error);
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

  const [similarList, setSimilarList] = useState([
    { id: 0, imageUrl: '', subtitle: '', title: '' },
  ]);

  const beanDetail = {
    roast: capsule.roast * 5,
    body: capsule.body * 5,
    acidity: capsule.acidity * 5,
    subtitle: capsule.brand,
    name: capsule.nameKr,
    isBookmarked: capsule.isBookmarked,
    imageUrl: capsule.imageUrl,
    description: capsule.description,
    aroma: capsule.aroma,
    product: beans || '',
    id: capsule.id,
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
              subtitle={item.subtitle}
              name={item.title}
              capsule_id={item.id}
              imgLink={item.imageUrl}
              key={index}
            />
          ))}
        </div>
      </div>
    </div>
  );
};
