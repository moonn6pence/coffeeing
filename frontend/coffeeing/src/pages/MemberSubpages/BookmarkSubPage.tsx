import React, { useEffect, useState } from 'react';
import { PaginationNew } from 'components/PaginationNew';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useParams } from 'react-router-dom';

export const BookmarkSubPage = () => {
  const [isCapsule, setIsCapsule] = useState(false);
  const { id } = useParams();
  const commonClass =
    'font-bold text-base hover:brightness-125 p-3 cursor-pointer';
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(-1);
  const [bookmarkList, setBookmarkList] = useState([]);

  const getBookmark = () => {
    const beans = isCapsule ? 'capsule' : 'coffee';
    privateRequest
      .get(`${API_URL}/member/${beans}/bookmark/${id}`, { params: { page: 0 } })
      .then((res) => {
        const data = res.data.data;
        console.log(data);
        setCurrentPage(data.page);
        setTotalPage(data.totalCount);
        setBookmarkList(data.bookmarkedElements);
        console.log(data.bookmarkedElements);
      });
  };

  useEffect(() => {
    getBookmark();
  }, [isCapsule]);

  return (
    <div className="bg-light p-12">
      <div className="w-full mb-10">
        <span
          className={
            isCapsule
              ? `${commonClass} text-light-roasting`
              : `${commonClass} text-cinamon-roasting border-b-2 border-cinamon-roasting`
          }
          onClick={() => {
            setIsCapsule(false);
          }}
        >
          원두
        </span>
        <span
          className={
            isCapsule
              ? `${commonClass} text-cinamon-roasting border-b-2 border-cinamon-roasting`
              : `${commonClass} text-light-roasting`
          }
          onClick={() => {
            setIsCapsule(true);
          }}
        >
          캡슐
        </span>
      </div>
      <PaginationNew
        currentPage={currentPage}
        totalPage={totalPage}
        isCapsule={isCapsule}
        setCurrentPage={() => {
          return;
        }}
        products={bookmarkList}
        isProfile={true}
      />
    </div>
  );
};
