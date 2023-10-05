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
  isSame?: boolean;
  replace?:boolean;
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
    isSame = false,
    onBookmarkChange,
    replace,
  } = props;
  const navigate = useNavigate();

  // 디테일 페이지로 이동
  const goDetail = () => {
    if (!isSame) {
      const beans = isCapsule ? 'capsule' : 'coffee';
      navigate(`/detail/${beans}/${id}`, {
        replace:replace,
        state: { id: `${id}` },
      });
  
      window.scrollTo(0, 0);
    } 
    }

  return (
    <div
      className={`h-94 w-full max-w-[282px] flex flex-col justify-center ${!isSame && 'cursor-pointer'}`}
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
        className={`w-36 h-36 m-auto mb-16 ${
          !isSame && 'hover:scale-110'
        }`}
      />
      <p className="text-sm text-center text-font-gray mb-2 h-5">
        {subtitle || '  '}
      </p>
      <p
        className={`text-base text-center mb-12 ${
          isSame ? 'break-words' : 'truncate'
        }`}
      >
        {name}
      </p>
    </div>
  );
};
