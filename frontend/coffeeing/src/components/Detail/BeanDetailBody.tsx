import React from 'react';
import { BeanRating } from './BeanRating';
import { CapsuleCard } from 'components/CapsuleCard';
import bookmarkOn from 'assets/bookmark_on.png';
import bookmarkOff from 'assets/detail/bookmark_off.png';

type BeanDetailBodyProps = {
  roast: number;
  body: number;
  acidity: number;
  brand: string;
  name: string;
  isBookmarked: boolean;
  imageUrl: string;
  description: string;
  aroma: string;
};

export const BeanDetailBody = (props: BeanDetailBodyProps) => {
  const {
    roast,
    body,
    acidity,
    brand,
    name,
    isBookmarked,
    imageUrl,
    description,
    aroma,
  } = props;

  const beanCharac = [
    { name: '로스팅', value: roast },
    { name: '바디감', value: body },
    { name: '산 미', value: acidity },
  ];

  return (
    <div className="bg-light flex w-300 h-450px justify-around mx-auto mt-10 items-center">
      <CapsuleCard
        capsule_id={0}
        brand={brand}
        name={name}
        imgLink={imageUrl}
      />
      <div className="flex flex-col items-end space-y-6 w-532px">
        <img
          src={isBookmarked ? bookmarkOn : bookmarkOff}
          alt="북마크"
          className="w-9 h-9 flex mr-0"
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
        <p className="w-full text-xl font-bold"># {aroma}</p>
      </div>
    </div>
  );
};
