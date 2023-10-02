import React, { Dispatch } from 'react';
import Next from 'assets/nextarrow.png';
import Prev from 'assets/prevarrow.png';
import DisNext from 'assets/disnextarrow.svg';
import DisPrev from 'assets/disprevarrow.svg';
import { BeanCard } from './BeanCard';
import { ReviewCard } from './Detail/ReviewCard';
import { ReviewProps } from './Detail/ReviewCard';

type Product = {
  id: number;
  nameEng: string;
  nameKr: string;
  regionEng?: string;
  regionKr?: string;
  brandEng?: string;
  brandKr?: string;
  imageUrl: string;
  isCapsule: boolean;
};

export type PaginationProps = {
  currentPage: number;
  totalPage: number;
  products?: Product[];
  reviews?: ReviewProps[];
  isCapsule: boolean;
  setCurrentPage: Dispatch<React.SetStateAction<number>>;
  isReview?: boolean;
};

export const PaginationNew = ({
  currentPage,
  totalPage,
  products,
  isCapsule,
  setCurrentPage,
  isReview = false,
  reviews,
}: PaginationProps) => {
  const commonClass = 'w-10 h-10 font-semibold text-base';
  const maxButtons = 10;
  const startPage = Math.max(0, currentPage - Math.floor(maxButtons / 2));
  const endPage = Math.min(totalPage, startPage + maxButtons - 1);
  return (
    <div>
      {isReview ? (
        <div className="flex flex-wrap">
          {reviews?.map((item) => {
            return <ReviewCard key={item.reviewId} memberReview={item} />;
          })}
        </div>
      ) : (
        <div className="w-300 grid grid-cols-4">
          {products &&
            products.map((item) => {
              return (
                <BeanCard
                  id={item.id}
                  subtitle={isCapsule ? item.brandKr : item.regionKr}
                  name={item.nameKr}
                  imgLink={item.imageUrl}
                  isCapsule={item.isCapsule}
                  key={item.id}
                />
              );
            })}
        </div>
      )}

      {/* 페이지네이션 버튼들 */}
      <div className="flex justify-center">
        {/* 맨 처음 버튼 */}
        <button onClick={() => setCurrentPage(0)} disabled={currentPage == 0}>
          처음으로
        </button>
        {/* 이전 버튼 */}
        <button
          onClick={() => setCurrentPage(currentPage - 1)}
          disabled={currentPage === 0}
          className="w-10 h-10"
        >
          <img src={currentPage === 0 ? DisPrev : Prev} />
        </button>
        {/* 페이지 숫자들 */}
        {Array.from({ length: endPage - startPage + 1 }, (_, index) => (
          <button
            key={index + 1}
            onClick={() => setCurrentPage(startPage + index)}
            className={
              startPage + index === currentPage
                ? `${commonClass} text-light-roasting`
                : `${commonClass}`
            }
          >
            {startPage + index + 1}
          </button>
        ))}
        {/* 다음 버튼 */}
        <button
          onClick={() => setCurrentPage(currentPage + 1)}
          disabled={currentPage === totalPage}
          className="w-10 h-10"
        >
          <img src={currentPage === totalPage ? DisNext : Next} />
        </button>
        {/* 맨 뒤로 버튼 */}
        <button
          onClick={() => setCurrentPage(totalPage)}
          disabled={currentPage === totalPage}
          className="pointer"
        >
          맨 뒤로
        </button>
      </div>
    </div>
  );
};
