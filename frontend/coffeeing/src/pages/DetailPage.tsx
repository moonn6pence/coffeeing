import React, { useEffect, useState } from 'react';
import { BeanDetailBody } from 'components/Detail/BeanDetailBody';
import { ReviewForm } from 'components/Detail/ReviewForm';
import { BeanCard } from 'components/BeanCard';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useNavigate, useParams } from 'react-router-dom';
import { MyReview } from 'components/Detail/MyReview';
import { ReviewEditModal } from 'components/Detail/ReviewEditModal';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import IonIcon from '@reacticons/ionicons';
import { DeleteAlert } from 'components/DeleteAlert';
import { PaginationNew } from 'components/PaginationNew';

export const DetailPage = () => {
  const { beans, id } = useParams();
  const isLogin = useSelector((state: RootState) => state.member.isLogin);
  const navigate = useNavigate();

  const [seeModal, setSeeModal] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(-1);
  const handleModal = () => {
    setSeeModal(!seeModal);
  };

  // 상품 정보 불러오기
  const fetchData = async () => {
    try {
      const response = await (isLogin
        ? privateRequest.get(`${API_URL}/product/${beans}/${id}`)
        : publicRequest.get(`${API_URL}/product/${beans}/${id}`));

      const data = response.data.data;
      console.log(data);
      setCapsule(data);
    } catch (error) {
      console.error(error);
      navigate('/no');
    }
  };

  useEffect(() => {
    fetchData();
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
    isReviewed: false,
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

  const handleDelete = async () => {
    const goDelete = await DeleteAlert();

    if (goDelete) {
      // console.log('진짜 지움');
      const review_id = capsule.memberReview.reviewId;

      privateRequest
        .delete(`${API_URL}/product/${beans}/review/${review_id}`)
        .then(() => {
          fetchData();
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  // 리뷰 가져오기
  const getReview = async () => {
    privateRequest
      .get(`${API_URL}/product/${beans}/${id}/review`, {
        params: { page: currentPage },
      })
      .then((res) => {
        // console.log(res.data.data.reviews);
        setReviews(res.data.data.reviews);
        setTotalPage(res.data.data.totalCount - 1);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  useEffect(() => {
    getReview();
  }, [currentPage]);

  useEffect(() => {
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
    imageUrl: capsule.imageUrl,
    description: capsule.description,
    aroma: capsule.aroma,
    product: beans || '',
    id: capsule.id,
  };

  return (
    <div>
      <BeanDetailBody {...beanDetail} isBookmarked={capsule.isBookmarked} />
      {isLogin ? (
        <div className="w-4/5 mt-10 mx-auto">
          <p className="text-2xl font-bold mb-3">리뷰 남기기</p>
          {capsule.isReviewed ? (
            <MyReview
              memberReview={capsule.memberReview}
              beans={beans}
              handleModal={handleModal}
              handleDelete={handleDelete}
            />
          ) : (
            <ReviewForm product_id={id} beans={beans} />
          )}
          {seeModal && (
            <ReviewEditModal
              beans={beans}
              handleModal={handleModal}
              reviewId={capsule.memberReview.reviewId}
              score={capsule.memberReview.score}
              content={capsule.memberReview.content}
            />
          )}
        </div>
      ) : (
        <div className="relative w-4/5 mx-auto">
          <div className="bg-review-blur w-full h-72 blur-sm mt-10"></div>
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

      <div className="w-4/5 mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">
          평균 평점 {capsule.averageScore.toFixed(1)}{' '}
          <span className="text-[#BE9E8B]">/ 5.0</span>
        </p>
        <div className="flex space-x-6">
          {reviews[0] ? (
            <PaginationNew
              currentPage={currentPage}
              totalPage={totalPage}
              reviews={reviews}
              isCapsule={beans === 'capsule' ? true : false}
              setCurrentPage={setCurrentPage}
              isReview={true}
            />
          ) : (
            <div className="w-300 flex flex-col h-30 items-center justify-center space-y-6">
              <IonIcon name="chatbubble-ellipses-outline" size="large" />
              <p>첫 리뷰를 남겨보세요</p>
            </div>
          )}
        </div>
      </div>
      <div className="w-4/5 mt-10 mx-auto">
        <p className="text-2xl font-bold mb-3">
          비슷한 {beans === 'capsule' ? '캡슐' : '원두'}
        </p>
        <div className="flex w-full justify-between">
          {similarList.map((item, index) => (
            <BeanCard
              subtitle={item.subtitle}
              name={item.title}
              id={item.id}
              imgLink={item.imageUrl}
              isCapsule={beans === 'capsule' ? true : false}
              key={index}
            />
          ))}
        </div>
      </div>
    </div>
  );
};
