import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { RootState } from "store/store";
import { BeanCard } from "components/BeanCard";
import { BeanRating } from "components/Detail/BeanRating";
import { useNavigate } from "react-router-dom";
import again from '../assets/again.png'
import loadingGif from '../assets/survey/loading.gif'
import { Toast } from "components/Toast";
import NoImg from '../assets/noprofile.png'

export const RecResultPage  = ()=>{
  const navigate = useNavigate();
  const survey =useSelector((state:RootState)=>state.survey);
  const {isLogin,profileImage} = useSelector((state:RootState)=>state.member)
  const [loading, setLoading] = useState(true);
  const [userInfo, setUserInfo] = useState({
    roast:0,
    acidity:0,
    body:0,
    imageUrl:'',
    nickname:'',
    isCapsule:false
  })

  const [products, setProducts] = useState([{
    id:0,
    imageUrl:'',
    subtitle:'',
    title:'',
  }])
  const sendPreference = async ()=>{
    const result = await savePreference(survey);
    if (result) {
      Toast.fire('저장되었습니다','','success')
    }
  }
  const getPreference = async ()=>{
    const result = await getSurveyResult(survey, isLogin);
    if (result) {
      setProducts(result.recommendation.products)
      setUserInfo((userInfo)=>({
        ...userInfo,
        roast:result.roast, 
        acidity:result.acidity,
        body:result.body,
        imageUrl:result.imageUrl,
        nickname:result.nickname,
        isCapsule:result.recommendation.isCapsule,
      }))
      if (result.imageUrl===null) {
        setUserInfo((userInfo)=>({
          ...userInfo,
          imageUrl:NoImg
        }))
      }
      setLoading(false)
      console.log(userInfo.imageUrl)
      console.log(profileImage)
    } else {
      console.log(result)
    }
  }

  useEffect(() => {
    const delay = 2000; 
    const timerId = setTimeout(() => {
      getPreference();
    }, delay);
    return () => {
      clearTimeout(timerId);
    };
  }, []);
  

  return(
    <div className="flex flex-col items-center">
      {loading 
      ? (
        <div className="flex flex-col items-center">
          <img src={loadingGif} />
          <p className="text-2xl">취향을 분석중입니다</p>
        </div>
      ) 
      : (
        <div className="w-3/4">
          {/* 상단부 */}
          <div className="mt-10 flex flex-col">
            <p className="text-2xl font-bold">
              {userInfo.nickname} <span>{isLogin ? '님' : ''}</span> 맞춤 {userInfo.isCapsule ? '캡슐' : '원두'} 추천
            </p>
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
              {products.map((item, index) => (
                <BeanCard
                  subtitle={item.subtitle}
                  name={item.title}
                  id={item.id}
                  imgLink={item.imageUrl}
                  isCapsule={userInfo.isCapsule}
                  key={index}
                />
              ))}
            </div>
          </div>

          <hr className="mx-15" />
          {/* 추천 다시 받기 */}
          <div className="mt-5 flex flex-row mx-15 justify-end items-center gap-1">
            <img src={again} className="w-4 h-4" alt="Again" />
            <p
              className="font-black cursor-pointer"
              onClick={() => navigate('/recommend-main')}
            >
              추천 다시 받기
            </p>
          </div>
          {/* 하단부 */}
          <div className="mt-2 bg-light mx-15 h-80 flex items-center justify-around">
            {isLogin&&(
              <div className=" flex flex-col gap-3 items-center w-0 md:w-70.5">
                <img src={userInfo.imageUrl} alt="Profile" className="hidden md:block w-44 h-44 rounded-full border-2" />
                <p className="hidden md:block">{userInfo.nickname}</p>
              </div>
            )}
            <div className="space-y-3 w-1/2">
              <div className="flex flex-row">
                <p className="text-xl font-black mb-10">내 취향 분석</p>

              </div>
              <BeanRating roast={userInfo.roast*5} acidity={userInfo.acidity*5} body={userInfo.body*5} />
            </div>
          </div>
          {/* 취향 분석 저장하기 버튼 */}
          {isLogin&&(
            <div className="w-full flex justify-center my-2">
              <p
                className="bg-half-light text-white p-3 rounded-full cursor-pointer"
                onClick={()=>sendPreference()}
                >내 취향 정보 저장하기</p>
            </div>
          )}
        </div>
      )}
    </div>
    )}
