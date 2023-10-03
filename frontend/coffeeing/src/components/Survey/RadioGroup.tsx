import React from "react";
import { RadioGroup } from "@headlessui/react";
import { CoffeeItem} from "util/constants";
import { RoastingTooltip } from "components/Tooltip/survey";
type RadioGroupProps = {
  selectedItem:string|number,
  setSelectedItem:any,
  itemList:CoffeeItem[],
  pageNum:number,
}

export const RadioGroupSingle = ({selectedItem, setSelectedItem, itemList,pageNum}:RadioGroupProps)=>{
  return(
    <RadioGroup 
      className={`${pageNum===5?'gap-3':'gap-10'} flex`} 
      value={selectedItem} 
      onChange={setSelectedItem}
      >
      {itemList.map((item)=>(
        <RadioGroup.Option key={item.label} value={item.label}>
          {({checked})=>(
            <div
            className={
              `${pageNum===0?'w-96 h-80':'w-64 h-60'}
              flex flex-col items-center justify-end rounded-xl
              ${checked ? 'bg-select-img' : ''} `}
            >
            <img
              className={
                `${pageNum===0?'w-80 h-72':pageNum===1?'w-48 h-48':'w-52 h-52'} 
                origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
              src={item.src}
            />
            <div className="flex flex-row items-baseline gap-1">
              <p className={`${pageNum===0?'text-xl mb-2':'mb-2'}`}>{item.name}</p>
              {pageNum===1&&item.toolTipDesc&&
                <RoastingTooltip label={item.toolTipDesc}/>
              }
            </div>
          </div>
          )}
        </RadioGroup.Option>
      ))}
    </RadioGroup>
  )
}