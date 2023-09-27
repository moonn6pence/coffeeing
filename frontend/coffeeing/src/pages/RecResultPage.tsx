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
  // 타입 추후 수정
  const [rec, setRec] = useState<ResultType>();
  const [userInfo, setUserInfo] = useState({
    roast:0,
    acidity:0,
    body:0,
    imageUrl:'',
    nickname:'',
  })
  const [products, setProducts] = useState([{
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
    result 
    ? 
      setProducts(result.products)
    : console.log(result)
  }
  useEffect( ()=>{
    sendPreference();
  },[])

  return(
    <div>
      <div className="flex w-300 justify-between">
        {/* {products.map((item, index) => (
          <BeanCard
            subtitle={item.subtitle}
            name={item.title}
            id={item.id}
            imgLink={item.imageUrl}
            isCapsule={beans === 'capsule' ? true : false}
            key={index}
          />
        ))} */}
      </div>

    </div>
  )
}