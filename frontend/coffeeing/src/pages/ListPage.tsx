import React, { useEffect, useState } from 'react';
import Carousel from 'components/Carousel';
import { publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { curationListProps } from 'components/Carousel';

type curationProps = {
  title: string;
  isCapsule: boolean;
  products: curationListProps[];
};
export const ListPage = () => {
  const [isCapsule, setIsCapsule] = useState(false);
  const [curationLists, setCurationLists] = useState<curationProps[]>([]);
  // 공통 CSS
  const commonClass = 'h-12 font-bold text-base hover:brightness-125';
  // active 상태에 따른 CSS
  const linkClass = isCapsule
    ? `${commonClass} text-cinamon-roasting`
    : `${commonClass} text-light-roasting`;

  const publicCuration = () => {
    publicRequest
      .get(`${API_URL}/curation/open`, { params: { isCapsule: isCapsule } })
      .then((res) => {
        console.log(res.data.data.curations);
        setCurationLists(res.data.data.curations);
      });
  };

  useEffect(() => {
    publicCuration();
  }, []);
  return (
    <div className="mt-10 flex flex-col items-center">
      <div className="text-left">
        <span className={linkClass}>원두</span>
        <span>캡슐</span>
      </div>
      {curationLists.map((item, index) => (
        <div key={index} className="">
          <p className="font-bold text-2xl">{item.title}</p>
          <Carousel curationList={item.products} isCapsule={item.isCapsule} />
        </div>
      ))}
    </div>
  );
};
