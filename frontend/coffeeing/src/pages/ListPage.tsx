import React, { useEffect, useState } from 'react';
import Carousel from 'components/Carousel';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { curationListProps } from 'components/Carousel';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';

type curationProps = {
  title: string;
  isCapsule: boolean;
  products: curationListProps[];
};
export const ListPage = () => {
  const [isCapsule, setIsCapsule] = useState(false);
  const [curationLists, setCurationLists] = useState<curationProps[]>([]);
  const isLogin = useSelector((state: RootState) => state.member.isLogin);

  // 공통 CSS
  const commonClass = 'font-bold text-base hover:brightness-125 p-3';

  const publicCuration = () => {
    publicRequest
      .get(`${API_URL}/curation/open`, { params: { isCapsule: isCapsule } })
      .then((res) => {
        console.log(res.data.data.curations);
        setCurationLists(res.data.data.curations);
      })
      .catch((err)=>{
        console.log(err.response)
      });
  };

  const privateCuration = () => {
    privateRequest.get(`${API_URL}/curation/custom`, { params: { isCapsule: isCapsule } })
      .then((res)=>{
        setCurationLists(res.data.data.curations)
      })
      .catch((err)=>{
        console.log(err.response)
      })
  }

  useEffect(() => {
    isLogin
    ? privateCuration()
    : publicCuration();
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
