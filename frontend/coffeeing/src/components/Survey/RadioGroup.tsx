import React from "react";
import { RadioGroup } from "@headlessui/react";
import { CoffeeItem } from "util/constants";

type RadioGroupProps = {
  selectedItem:string,
  setSelectedItem:any,
  itemList:CoffeeItem[],
}

export const RadioGroupSingle = ({selectedItem, setSelectedItem, itemList}:RadioGroupProps)=>{
  return(
    <RadioGroup className="flex gap-10" value={selectedItem} onChange={setSelectedItem}>
      {itemList.map((item)=>(
        <RadioGroup.Option key={item.label} value={item.label}>
          {({checked})=>(
            <div
            className={`w-96 h-80 flex flex-col items-center ${
              checked ? 'bg-select-img' : ''
            } rounded-xl`}
            >
            <img
              className={`w-80 h-72  origin-center transform hover:scale-105 hover:translate-y-[-10px] `}
              src={item.src}
            />
            <p className="text-xl">{item.name}</p>
          </div>
          )}
        </RadioGroup.Option>
      ))}
    </RadioGroup>
  )
}