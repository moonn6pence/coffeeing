import React, { useEffect, useState } from 'react';
import { BeanRating } from './BeanRating';
import { BeanCard } from 'components/BeanCard';
import bookmarkOn from 'assets/bookmark_on.png';
import bookmarkOff from 'assets/detail/bookmark_off.png';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import { Toast } from 'components/Toast';

type BeanDetailBodyProps = {
  roast: number;
  body: number;
  acidity: number;
  subtitle: string;
  name: string;
  isBookmarked: boolean;
  imageUrl: string;
  description: string;
  aroma: string;
  product: string;
  id: number;
};

export const BeanDetailBody = (props: BeanDetailBodyProps) => {
  const {
    roast,
    body,
    acidity,
    subtitle,
    name,
    isBookmarked,
    imageUrl,
    description,
    aroma,
    product,
    id,
  } = props;

  const [isBooked, setIsBooked] = useState<boolean>(false);
  const aromaList = aroma.split(', ');
  const isLogin = useSelector((state: RootState) => state.member.isLogin);

  useEffect(() => {
    setIsBooked(isBookmarked);
    // console.log('help = ', isBookmarked);
  }, [isBookmarked]);

  const handleBookmark = () => {
    if (isLogin) {
      privateRequest
        .post(`${API_URL}/product/${product}/${id}/bookmark`)
        .then((res) => {
          // console.log(res.data.data);
          if (!res.data.data.result) {
            setIsBooked(false);
          } else {
            setIsBooked(true);
          }
        });
    } else {
      Toast.fire('로그인 후 이용해주세요.', '', 'error');
    }
  };

  return (
    <div className="bg-light flex w-4/5 h-fit py-6 justify-around mx-auto mt-10 items-center flex-wrap">
      <div className="w-70.5">
        <BeanCard
          id={id}
          subtitle={subtitle}
          name={name}
          imgLink={imageUrl}
          isCapsule={product === 'capsule' ? true : false}
          isSame={true}
        />
      </div>
      <div className="flex flex-col items-end space-y-6 w-532px">
        <img
          src={isBooked ? bookmarkOn : bookmarkOff}
          alt="북마크"
          className="w-9 h-9 flex mr-0 cursor-pointer"
          onClick={handleBookmark}
        />
        <div className="space-y-3 w-full">
          <BeanRating roast={roast} body={body} acidity={acidity} />
        </div>
        <p className="w-full max-w-full">{description}</p>
        <p className="w-full text-xl font-bold flex justify-between">
          {aromaList.map((flavor, index) => (
            <span key={index}># {flavor}</span>
          ))}
        </p>
      </div>
    </div>
  );
};
