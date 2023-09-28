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
  const commonClass = 'font-bold text-base hover:brightness-125 p-3';

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
  }, [isCapsule]);
  return (
    <div className="mt-10 flex flex-col w-4/5 mx-auto">
      <div className="w-full mb-10">
        <span className={isCapsule
      ? `${commonClass} text-light-roasting`
      : `${commonClass} text-cinamon-roasting border-b-2 border-cinamon-roasting`} onClick={()=>{setIsCapsule(false)}}>원두</span>
        <span className={isCapsule
      ? `${commonClass} text-cinamon-roasting border-b-2 border-cinamon-roasting`
      : `${commonClass} text-light-roasting`} onClick={()=>{setIsCapsule(true)}}>캡슐</span>
      </div>
      {curationLists.map((item, index) => (
        <div key={index}>
          <p className="font-bold text-2xl">{item.title}</p>
          <Carousel curationList={item.products} isCapsule={item.isCapsule} />
        </div>
      ))}
    </div>
  );
};
