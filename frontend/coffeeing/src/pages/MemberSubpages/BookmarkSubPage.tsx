import React, { useEffect, useState } from 'react';
import { PaginationNew } from 'components/PaginationNew';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useParams } from 'react-router-dom';
import noBookmark from 'assets/nobookmark.png';

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
        // console.log(data);
        setCurrentPage(data.page);
        setTotalPage(data.totalCount - 1);
        setBookmarkList(data.bookmarkedElements);
        // console.log(data.bookmarkedElements);
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
      {bookmarkList[0] ? (
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
      ) : (
        <div className="flex flex-col items-center space-y-12">
          <img src={noBookmark} alt="북마크 없음" className="w-1/3" />
          <p>찜한 {isCapsule ? '캡슐이' : '원두가'} 없어요</p>
        </div>
      )}
    </div>
  );
};
