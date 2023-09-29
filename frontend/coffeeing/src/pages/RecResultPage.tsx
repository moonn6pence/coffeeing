import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { RootState } from "store/store";
import { BeanCard } from "components/BeanCard";
import { BeanRating } from "components/Detail/BeanRating";
import profile from '../assets/profile.svg'
import IonIcon from "@reacticons/ionicons";
import { useNavigate } from "react-router-dom";
import again from '../assets/again.png'
type ProductsType = {
  id:number,
  imageUrl:string,
  subtitle:string,
  title:string,
}

type ResultType = {
  roast:number,
  acidity:number,
  body:number,
  imageUrl:string,
  nickname:string,
  recommendation:{
    isCapsule:boolean,
    products: ProductsType[]
  },
}

export const RecResultPage  = ()=>{
  const navigate = useNavigate();
  const survey =useSelector((state:RootState)=>state.survey);
  const {isLogin} = useSelector((state:RootState)=>state.member)
  const [userInfo, setUserInfo] = useState({
    roast:2,
    acidity:5,
    body:3,
    imageUrl:'',
    nickname:'',
  })
  const [products, setProducts] = useState([
    { title: '니카라과', subtitle: '네스프레소', id: 2, imageUrl: '/' ,isCapsule:true},
    { title: '코지', subtitle: '네스프레소', id: 3, imageUrl: '/',isCapsule:true },
    { title: '볼루토', subtitle: '네스프레소', id: 5, imageUrl: '/' ,isCapsule:true},
    { title: '베네치아', subtitle: '네스프레소', id: 6, imageUrl: '/' ,isCapsule:true},
  ])
  // const [products, setProducts] = useState([{
  //   isCapsule:true,
  //   id:0,
  //   imageUrl:'',
  //   subtitle:'',
  //   title:''
  // }])
  const sendPreference = async ()=>{
    const result = await savePreference(survey);
    console.log(result)
  }
  const getPreference = async ()=>{
    const result = await getSurveyResult(survey);
    if (result) {
      setProducts(result.recommendation.products)
      setProducts((products)=>({...products,isCapsule:result.recommendation.isCapsule}))
      setUserInfo((userInfo)=>({
        ...userInfo,
        roast:result.roast, 
        acidity:result.acidity,
        body:result.body,
        imageUrl:result.imageUrl,
        nickname:result.nickname,
      }))
    } else {
      console.log(result)
    }
  }
  useEffect( ()=>{
    // sendPreference();
  },[])

  return(
    <div className="flex flex-col items-center">
      {/* [Recommendation Result] */}
      <div className="mt-10 flex flex-col">
        <p className="text-xl font-bold">{userInfo.nickname} <span>{isLogin?'님':''}</span> 맞춤 {products[0].isCapsule ? '캡슐' : '원두'} 추천</p>
        <div className="flex w-300 justify-between">
          {products.map((item, index) => (
            <BeanCard
              subtitle={item.subtitle}
              name={item.title}
              id={item.id}
              imgLink={item.imageUrl}
              isCapsule={item.isCapsule}
              key={index}
            />
          ))}
        </div>
      </div>
      <hr className="w-300"/>
      {/* [My Preference Result] */}
      <div className="mt-5 flex flex-row w-300 justify-end items-center gap-1">
        <img src={again} className="w-4 h-4"/>
        <p 
          className="font-black cursor-pointer"
          onClick={()=>navigate('/recommend-main')}
          >추천 다시 받기</p>
      </div>

      <div className="mt-2 bg-light w-300 h-80 flex items-center justify-around">
        {/* [IF LOGIN] [Profile Img & NickName] */}
        <div className="flex flex-col gap-3 items-center w-70.5">
          <img src={profile} className="w-44 h-44 rounded-full border-2 "/>
          {/* <img src={userInfo.imageUrl} /> */}
          <p>닉네임</p>
          <p>{userInfo.nickname}</p>
        </div>
        {/* [Preference] */}
        <div className="space-y-3 w-532px">
          <p className="text-xl font-black mb-10">내 취향 분석</p>
          <div className="flex w-full justify-between">
            <span className="text-xl font-bold my-auto">로스팅</span> 
            <BeanRating rate={userInfo.roast}/>
          </div>
          <div className="flex w-full justify-between">
            <span className="text-xl font-bold my-auto">산미</span> 
            <BeanRating rate={userInfo.acidity}/>
          </div>
          <div className="flex w-full justify-between">
            <span className="text-xl font-bold my-auto">바디감</span> 
            <BeanRating rate={userInfo.body}/>
          </div>
        </div>
      </div>
    </div>
  )
}