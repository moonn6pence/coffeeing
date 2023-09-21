import React, {Dispatch, SetStateAction,useState} from "react"
import { useSelector, useDispatch } from "react-redux"
import chocolate from '../../assets/survey/flavor/chocolate.png'
import floral from '../../assets/survey/flavor/floral.png'
import fruity from '../../assets/survey/flavor/grapes.png'
import nutty from '../../assets/survey/flavor/nutty.png'
import spicy from '../../assets/survey/flavor/spicy.png'
import sweet from '../../assets/survey/flavor/sweet.png'
import { AppDispatch, RootState } from 'store/store';
import { saveFlavorNote } from "store/surveySlice"

export const FlavorNoteSelect = () => {
  const survey = useSelector((state:RootState)=>state.survey)
  const dispatch = useDispatch<AppDispatch>();
  const [selectedChocolate, setSelectedChocolate] = useState(false);
  const [selectedFloral, setSelectedFloral] = useState(false);
  const [selectedFruity, setSelectedFruity] = useState(false);
  const [selectedNutty, setSelectedNutty] = useState(false);
  const [selectedSpicy, setSelectedSpicy] = useState(false);
  const [selectedSweety, setSelectedSweety] = useState(false);
  const [myFlavor, setMyFlavor] = useState(Array<string>);

  const data = [
    {src:chocolate, label:'초콜릿', isSelected:selectedChocolate, setIsSelected:setSelectedChocolate, keyword:'chocolate'},
    {src:floral, label:'플로럴', isSelected:selectedFloral, setIsSelected:setSelectedFloral, keyword:'floral'},
    {src:fruity, label:'과일', isSelected:selectedFruity, setIsSelected:setSelectedFruity, keyword:'fruity'},
    {src:nutty, label:'견과류', isSelected:selectedNutty, setIsSelected:setSelectedNutty, keyword:'nutty'},
    {src:spicy, label:'매콤함', isSelected:selectedSpicy, setIsSelected:setSelectedSpicy, keyword:'spicy'},
    {src:sweet, label:'달콤함', isSelected:selectedSweety, setIsSelected:setSelectedSweety, keyword:'sweety'},
  ]

  const handleFlavorSelect = (isSelected:boolean, setIsSelected:Dispatch<SetStateAction<boolean>>, keyword:string)=>{
    if (myFlavor.includes(keyword)) {
      setMyFlavor(myFlavor.filter(item=>item!==keyword))
      setIsSelected(!isSelected)
    } else {
      setMyFlavor([...myFlavor, keyword])
      setIsSelected(!isSelected)
    }
  }

  return(
    <div className='flex flex-col items-center gap-10 mt-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='font-bold'>어떤 맛이나 향을 좋아하시나요?</p>
        <p className='relative w-560px h-2.5 rounded-lg bg-process-bar'>
          <p className={`absolute botton-0 left-0  h-2.5 rounded-lg bg-light-roasting`}></p>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className='grid grid-rows-2 grid-flow-col gap-10 '>
        {
          data.map((item) => {
            const { src, label, isSelected, setIsSelected, keyword } = item;
            return (
              <div className={`w-64 h-60 flex flex-col items-center ${isSelected ? 'bg-select-img' : ''} rounded-xl`} key={src}>
                <img
                  className={`w-52 h-52 origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
                  src={src}
                  onClick={()=>handleFlavorSelect(isSelected, setIsSelected, keyword)}
                />
                <p>{label}</p>
              </div>
            );
          })
        }
      </div>
      <button onClick={()=>dispatch(saveFlavorNote(myFlavor))}>추가</button>
    </div>
  )
}