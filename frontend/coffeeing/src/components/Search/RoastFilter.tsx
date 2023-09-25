import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import roast from '../../assets/search/roast.png'
import acid from '../../assets/search/acitity.png'
import { DropDown} from "./DropDown";

export const RoastFilter = () => {
  const [view, setView] = useState(false);
  const [filteredRoast, setFilteredRoast] = useState<number[]>([]);
  const [lightRoast, setLightRoast] = useState(false);
  const [mediumLightRoast, setMediumLightRoast] = useState(false);
  const [mediumRoast, setMediumRoast] = useState(false);
  const [mediumDarkRoast, setMediumDarkRoast] = useState(false);
  const [darkRoast, setDarkRoast] = useState(false);

  const data = [
    {label:'라이트', num:0.2, roast:lightRoast, setRoast:setLightRoast},
    {label:'미디엄 라이트', num:0.4, roast:mediumLightRoast, setRoast:setMediumLightRoast},
    {label:'미디엄', num:0.6, roast:mediumRoast, setRoast:setMediumRoast},
    {label:'미디엄 다크', num:0.8, roast:mediumDarkRoast, setRoast:setMediumDarkRoast},
    {label:'다크 ', num:1, roast:darkRoast, setRoast:setDarkRoast},
  ]

  const handleRoast = (num:number, roast:boolean, setRoast:Dispatch<SetStateAction<boolean>>)=>{
    // 로스팅 선택 표시
    setRoast(!roast)
    // 선택된 로스팅 list update
    if (filteredRoast.includes(num)){
      setFilteredRoast(filteredRoast.filter((value)=>value!==num));
    } else {
      setFilteredRoast([...filteredRoast, num])
    }
  }

  useEffect(()=>{
    console.log(filteredRoast)
  },[filteredRoast])

  return(
    <div className="flex flex-row gap-3" >
      {/* 로스팅  */}
      <div className="flex flex-col">
        <div onClick={()=>setView(!view)} className="w-36 h-8 border border-cinamon-roasting rounded-3xl flex gap-2 items-center justify-center">
          <img className="w-4 h-5" src={roast}/>
          <span>로스팅 정도</span>
          <span>{!view ? '^' : '⌄'}</span>
        </div>
        {view && <DropDown data={data} handleFilter={handleRoast}/>}
      </div>
      {/* 산미 */}
      <div>
        <div onClick={()=>setView(!view)} className="w-36 h-8 border border-cinamon-roasting rounded-3xl flex gap-2 items-center justify-center">
          <img className="w-4 h-5" src={acid} />
          <span>산미</span>
          <span>{!view ? '^' : '⌄'}</span>
        </div>
      </div>
    </div>
  )
}