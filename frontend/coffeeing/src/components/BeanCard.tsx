import React from 'react';
import bookmarkOn from 'assets/bookmark_on.png';
import { useNavigate } from 'react-router-dom';

interface ICardProps {
  id: number;
  subtitle?: string;
  name: string;
  imgLink: string;
  isCapsule: boolean;
  isProfile?: boolean;
  onBookmarkChange?: (id: number) => void;
}

export const BeanCard = (props: ICardProps) => {
  const {
    id,
    subtitle,
    name,
    imgLink,
    isCapsule,
    isProfile = false,
    onBookmarkChange,
  } = props;
  const navigate = useNavigate();

  // 디테일 페이지로 이동
  const goDetail = () => {
    const beans = isCapsule ? 'capsule' : 'coffee';
    navigate(`/detail/${beans}/${id}`, {
      state: { id: `${id}` },
    });
  };

  return (
    <div
      className="h-94 w-70.5 flex flex-col justify-center cursor-pointer"
      onClick={goDetail}
    >
      {isProfile ? (
        <div className="flex flex-row justify-end">
          <button onClick={() => onBookmarkChange && onBookmarkChange(id)}>
            <img src={bookmarkOn} alt="북마크" className="w-6 h-6 mt-6 mr-6" />
          </button>
        </div>
      ) : (
        ''
      )}
      <img
        src={imgLink}
        alt="사진"
        className="w-36 h-36 m-auto mb-16 hover:scale-110"
      />
      <p className="text-sm text-center text-font-gray mb-2">{subtitle}</p>
      <p className="text-base text-center mb-12">{name}</p>
    </div>
  );
};
