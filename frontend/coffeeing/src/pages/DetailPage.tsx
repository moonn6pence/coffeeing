import React, { useEffect, useState } from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';
import { Pagination } from 'components/Pagination';
import { CapsuleCard } from 'components/CapsuleCard';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useNavigate, useParams } from 'react-router-dom';
import { MyReview } from 'components/Detail/MyReview';
import { ReviewEditModal } from 'components/Detail/ReviewEditModal';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import IonIcon from '@reacticons/ionicons';

export const DetailPage = () => {
  const { beans, id } = useParams();
  const isLogin = useSelector((state: RootState) => state.member.isLogin);
  const navigate = useNavigate();

  const [seeModal, setSeeModal] = useState(false);
  const handleModal = () => {
    setSeeModal(!seeModal);
  };

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
    isBookmarked: false,
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

  useEffect(() => {
    // 리뷰들 불러오기
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
    // 비슷한 상품 받아오기
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
      {isLogin ? (
        <div className="w-fit mt-10 mx-auto">
          <p className="text-2xl font-bold mb-3">리뷰 남기기</p>
          {capsule.isReviewed ? (
            <MyReview
              memberReview={capsule.memberReview}
              handleModal={handleModal}
            />
          ) : (
            <ReviewForm product_id={id} beans={beans} />
          )}
          {seeModal ? (
            <ReviewEditModal
              score={3}
              content="마시씀"
              beans={beans}
              reviewId={1}
              handleModal={handleModal}
              // reviewId={capsule.memberReview.reviewId}
              // score={capsule.memberReview.score}
              // content={capsule.memberReview.content}
            />
          ) : (
            ''
          )}
        </div>
      ) : (
        <div className="relative w-300 mx-auto">
          <div className="bg-review-blur w-300 h-72 blur-sm mt-10"></div>
          <button
            className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-my-black text-white rounded-3xl px-22px py-3"
            onClick={() => {
              navigate('/login');
            }}
          >
            로그인 후 이용해주세요
          </button>
        </div>
      )}

      <div className="w-fit mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">
          평균 평점 {capsule.averageScore.toFixed(1)}{' '}
          <span className="text-[#BE9E8B]">/ 5.0</span>
        </p>
        <div className="flex space-x-6">
          {reviews[0] ? (
            <Pagination limit={6} contentList={reviews} />
          ) : (
            <div className="w-300 flex flex-col h-30 items-center justify-center space-y-6">
              <IonIcon name="chatbubble-ellipses-outline" size="large" />
              <p>첫 리뷰를 남겨보세요</p>
            </div>
          )}
        </div>
      </div>
      <div className="w-fit mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">
          비슷한 {beans === 'capsule' ? '캡슐' : '원두'}
        </p>
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
