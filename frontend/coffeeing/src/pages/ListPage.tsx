import React, { useEffect, useState } from 'react';
import Carousel from 'components/Carousel';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { curationListProps } from 'components/Carousel';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import { BeanCard } from 'components/BeanCard';

type curationProps = {
  title: string;
  isCapsule: boolean;
  products: curationListProps[];
};
export const ListPage = () => {
  const [isCapsule, setIsCapsule] = useState(false);
  const [curationLists, setCurationLists] = useState<curationProps[]>([]);
  const [privateCurationLists, setPrivateCurationLists] = useState<
    curationProps[]
  >([]);
  const isAfterSurvey = useSelector(
    (state: RootState) => state.member.isAfterSurvey,
  );
  const isLogin = useSelector((state: RootState) => state.member.isLogin);

  // 공통 CSS
  const commonClass =
    'font-bold text-base hover:brightness-125 p-3 cursor-pointer';

  const getCuration = async () => {
    try {
      publicRequest
        .get(`${API_URL}/curation/open`, {
          params: { isCapsule: isCapsule },
        })
        .then((res) => {
          // console.log(res.data.data);
          setCurationLists(res.data.data.curations);
        });

      if (isLogin && isAfterSurvey) {
        privateRequest
          .get(`${API_URL}/curation/custom`, {
            params: { isCapsule: isCapsule },
          })
          .then((res) => {
            setPrivateCurationLists(res.data.data.curations);
          });
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getCuration();
  }, [isCapsule]);

  return (
    <div className="mt-10 flex flex-col w-4/5 mx-auto mb-20">
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
      {curationLists.map((item, index) => {
        if (item.products.length < 4) {
          return (
            <div key={index}>
              <p className="font-bold text-2xl">{item.title}</p>
              <div className="grid grid-cols-4 w-full pl-10">
                {item.products.map((product, index) => (
                  <BeanCard
                    id={product.id}
                    imgLink={product.imageUrl}
                    name={product.title}
                    subtitle={product.subtitle}
                    isCapsule={item.isCapsule}
                    key={index}
                  />
                ))}
              </div>
            </div>
          );
        }
        return (
          <div key={index}>
            <p className="font-bold text-2xl">{item.title}</p>
            <Carousel curationList={item.products} isCapsule={item.isCapsule} />
          </div>
        );
      })}
      {isAfterSurvey &&
        privateCurationLists.map((item, index) => {
          if (item.products.length < 4) {
            return (
              <div key={index}>
                <p className="font-bold text-2xl">{item.title}</p>
                <div className="grid grid-cols-4 w-full pl-10">
                  {item.products.map((product, index) => (
                    <BeanCard
                      id={product.id}
                      imgLink={product.imageUrl}
                      name={product.title}
                      subtitle={product.subtitle}
                      isCapsule={item.isCapsule}
                      key={index}
                    />
                  ))}
                </div>
              </div>
            );
          }
          return (
            <div key={index}>
              <p className="font-bold text-2xl">{item.title}</p>
              <Carousel
                curationList={item.products}
                isCapsule={item.isCapsule}
              />
            </div>
          );
        })}
    </div>
  );
};
