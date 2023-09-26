import React, { useState } from 'react';
import { BeanRating } from './BeanRating';
import { BeanCard } from 'components/BeanCard';
import bookmarkOn from 'assets/bookmark_on.png';
import bookmarkOff from 'assets/detail/bookmark_off.png';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

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

  const [isBooked, setIsBooked] = useState(isBookmarked);

  const beanCharac = [
    { name: '로스팅', value: roast },
    { name: '바디감', value: body },
    { name: '산 미', value: acidity },
  ];

  const aromaList = aroma.split(', ');

  const handleBookmark = () => {
    privateRequest
      .post(`${API_URL}/product/${product}/${id}/bookmark`)
      .then((res) => {
        if (res.data.data.result) {
          setIsBooked(false);
        } else {
          setIsBooked(true);
        }
      });
  };

  return (
    <div className="bg-light flex w-300 h-450px justify-around mx-auto mt-10 items-center">
      <BeanCard
        id={id}
        subtitle={subtitle}
        name={name}
        imgLink={imageUrl}
        isCapsule={product === 'capsule' ? true : false}
      />
      <div className="flex flex-col items-end space-y-6 w-532px">
        <img
          src={isBooked ? bookmarkOn : bookmarkOff}
          alt="북마크"
          className="w-9 h-9 flex mr-0 cursor-pointer"
          onClick={handleBookmark}
        />
        <div className="space-y-3 w-full">
          {beanCharac.map((charac) => (
            <div className="flex w-full justify-between" key={charac.name}>
              <span className="text-xl font-bold my-auto">{charac.name}</span>
              <BeanRating rate={charac.value} />
            </div>
          ))}
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
