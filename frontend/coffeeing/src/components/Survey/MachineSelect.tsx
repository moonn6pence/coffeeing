import nespresso from '../../assets/survey/machine/nespresso-removebg-preview.png'
import nespresso_burtuo from '../../assets/survey/machine/nespresso_b-removebg-preview.png'
import dolce from '../../assets/survey/machine/dolce-removebg-preview.png'
import illi from '../../assets/survey/machine/illi-removebg-preview.png'
import unknown from '../../assets/survey/machine/image_49-removebg-preview.png'

import React,{Dispatch, SetStateAction, useState}  from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { AppDispatch, RootState } from 'store/store';

export const MachineSelect = () => {
  const survey = useSelector((state:RootState)=>state.survey)
  const widthClass = `w-${survey.currentPage}/${survey.totalPage}`;
  const [selectedNespresso, setSelectedNespresso] = useState(false);
  const [selectedNespressoB, setSelectedNespressoB] = useState(false);
  const [selectedDolce, setSelectedDolce] = useState(false);
  const [selectedIlli, setSelectedIlli] = useState(false);
  const [selectedUnknown, setSelectedUnknown] = useState(false);
  const [myMachine, setMyMachine] = useState(-1);
  const dispatch = useDispatch<AppDispatch>();

  const data = [
    {src:nespresso, label:'네스프레소',isSelected:selectedNespresso, setIsSelected:setSelectedNespresso,num:1},
    {src:nespresso_burtuo, label:'네스프레소 버추오',isSelected:selectedNespressoB, setIsSelected:setSelectedNespressoB, num:2},
    {src:dolce, label:'돌체구스토',isSelected:selectedDolce, setIsSelected:setSelectedDolce, num:3},
    {src:illi, label:'네스프레소',isSelected:selectedIlli, setIsSelected:setSelectedIlli, num:4},
    {src:unknown, label:'기타', isSelected:selectedUnknown,setIsSelected:setSelectedUnknown, num:5}
  ]

  const handleMachineSelect = (num:number, isSelected:boolean, setIsSelected:Dispatch<SetStateAction<boolean>>)=>{
    if (myMachine==-1) {
      setMyMachine(num)
      setIsSelected(!isSelected)
    } else if (myMachine==num) {
      setMyMachine(-1)
      setIsSelected(!isSelected)
    } else {
      alert('하나만 선택해주세요')
    }
  }

  return(
    <div className='flex flex-col items-center gap-5 mt-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='font-bold'>커피 머신 선택</p>
        <p className='relative w-560px h-2.5 rounded-lg bg-process-bar'>
          <p className={`absolute botton-0 left-0 ${widthClass} h-2.5 rounded-lg bg-light-roasting`}></p>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className='flex flex-row gap-10'>
        {
          data.map((item) => {
            const { src, label, isSelected, setIsSelected, num } = item;
            return (
              <div className={`w-64 h-60 flex flex-col items-center ${isSelected ? 'bg-select-img' : ''} rounded-xl`} key={num}>
                <img
                  className={`w-52 h-52 origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
                  src={src}
                  onClick={()=>handleMachineSelect(num, isSelected, setIsSelected)}
                />
                <p>{label}</p>
              </div>
            );
          })
        }
      </div>
    </div>
  )
}