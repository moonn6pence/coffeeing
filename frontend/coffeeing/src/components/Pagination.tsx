import React, { useState } from 'react';
import { ReviewCard } from './Detail/ReviewCard';
import { ReviewProps } from './Detail/ReviewCard';
import Next from 'assets/nextarrow.png';
import Prev from 'assets/prevarrow.png';
import DisNext from 'assets/disnextarrow.svg';
import DisPrev from 'assets/disprevarrow.svg';

type PaginationProps = {
  limit: number;
  contentList: ReviewProps[];
};

export const Pagination = (props: PaginationProps) => {
  const { limit, contentList } = props;
  const commonClass = 'w-10 h-10 font-semibold text-base';

  // 현재 보여주는 페이지
  const [page, setPage] = useState(1);

  // 현재 페이지 첫 게시물의 인덱스
  const offset = (page - 1) * limit;

  // 총 페이지 수
  const numPages = Math.ceil(contentList.length / limit);
  const pageNums = new Array(numPages).fill(0);

  return (
    <div>
      {/* 나중에 리뷰쪽 페이지네이션인지 프로필쪽인지 구분하는 로직 추가 필요 */}
      {/* 리뷰 카드들 */}
      <div className="flex flex-wrap w-300 justify-between">
        {contentList.slice(offset, offset + limit).map((item, index) => (
          <ReviewCard memberReview={item} key={index} />
        ))}
      </div>
      {/* 페이지네이션 버튼들 */}
      <div className="flex justify-center">
        <button
          onClick={() => setPage(page - 1)}
          disabled={page === 1}
          className="w-10 h-10"
        >
          <img src={page === 1 ? DisPrev : Prev} />
        </button>
        {pageNums.map((item, index) => (
          <button
            key={index + 1}
            onClick={() => setPage(index + 1)}
            className={
              index + 1 === page
                ? `${commonClass} text-light-roasting`
                : `${commonClass}`
            }
          >
            {index + 1}
          </button>
        ))}
        <button
          onClick={() => setPage(page + 1)}
          disabled={page === numPages}
          className="w-10 h-10"
        >
          <img src={page === numPages ? DisNext : Next} />
        </button>
      </div>
    </div>
  );
};
