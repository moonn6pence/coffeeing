import React from 'react';
import bookmarkOn from 'assets/bookmark_on.png';
import { useLocation, useNavigate } from 'react-router-dom';

interface ICardProps {
  capsule_id: number;
  subtitle: string;
  name: string;
  imgLink: string;
}

export const CapsuleCard = (props: ICardProps) => {
  const { capsule_id, subtitle, name, imgLink } = props;
  const navigate = useNavigate();
  const { pathname } = useLocation();

  // 디테일 페이지로 이동
  const goDetail = () => {
    navigate(`/detail/capsule/${capsule_id}`, {
      state: { id: `${capsule_id}` },
    });
  };

  return (
    <div
      className="h-94 w-70.5 flex flex-col justify-center cursor-pointer"
      onClick={goDetail}
    >
      {/* 이후에 찜 여부에 따라 북마크 보여주기 여부 결정 로직 추가 할 예정 */}
      {pathname === 'myprofile' ? (
        <div className="flex flex-row justify-end">
          <img src={bookmarkOn} alt="북마크" className="w-6 h-6 mt-6 mr-6" />
        </div>
      ) : (
        ''
      )}
      <img
        src={imgLink}
        alt="캡슐 사진"
        className="w-36 h-36 m-auto mb-16 hover:scale-110"
      />
      <p className="text-sm text-center text-font-gray mb-2">{subtitle}</p>
      <p className="text-base text-center mb-12">{name}</p>
    </div>
  );
};
