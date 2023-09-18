import React, {Dispatch, SetStateAction, useState} from "react";
import { useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';

export const RoastFilter = () => {
  const [view, setView] = useState(false);
  const dispatch = useDispatch<AppDispatch>();
  const [filteredRoast, setFilteredRoast] = useState([]);
  const [lightRoast, setLightRoast] = useState(false);
  // const [mediumLightRoast, setLightRoast] = useState(false);
  const [mediumRoast, setMediumRoast] = useState(false);
  const [darkRoast, setDarkRoast] = useState(false);
  const data = [
    {label:'라이트 로스팅', num:0.2, roast:lightRoast, setRoast:setLightRoast},
    {label:'미디엄 로스팅', num:0.2, roast:mediumRoast, setRoast:setMediumRoast},
    {label:'다크 로스팅', num:0.2, roast:darkRoast, setRoast:setDarkRoast},
  ]
  const handleRoast = (num:number, roast:boolean, setRoast:Dispatch<SetStateAction<boolean>>)=>{
    // 로스팅 선택 표시
    setRoast(!roast)
    // 선택된 로스팅 list update
    if (num in filteredRoast){
        console.log('a')
    } else {
      // setFilteredRoast()
      setFilteredRoast([...filteredRoast, num])
    }
    
  }

  return(
    <div >
      <p onClick={()=>setView(!view)}>로스팅 정도 {view ? '^' : '⌄'}</p>
      
      {view && 
        data.map((item)=>{
          const {label, num, roast, setRoast} = item;
          return(
            <div
              className={`${roast ? 'bg-slate-500':'bg-white'}`}
              onClick={()=>handleRoast(num, roast, setRoast)}  
              key={num}
            >{label}</div>
          )
        })
      }
    </div>
  )
}