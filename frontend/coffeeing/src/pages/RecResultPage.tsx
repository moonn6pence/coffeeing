import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getSurveyResult, savePreference } from "service/survey/recommend";
import { RootState } from "store/store";
import { BeanCard } from "components/BeanCard";

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
  const [userInfo, setUserInfo] = useState({
    roast:0,
    acidity:0,
    body:0,
    imageUrl:'',
    nickname:'',
  })
  const [products, setProducts] = useState([{
    isCapsule:true,
    id:0,
    imageUrl:'',
    subtitle:'',
    title:''
  }])
  const survey =useSelector((state:RootState)=>state.survey);
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
    sendPreference();
  },[])

  return(
    <div>
      <p>{userInfo.nickname}님 맞춤 {products[0].isCapsule ? '캡슐' : '원두'} 추천</p>
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
  )
}