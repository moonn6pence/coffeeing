import React, { Dispatch, SetStateAction } from "react";
import { FilterItem } from "util/constants";
import IonIcon from "@reacticons/ionicons";
type SelectedTagProps = {
  src: string;
  selectedItem: FilterItem[];
  setSelectedItem: Dispatch<SetStateAction<FilterItem[]>>;
};

export const SelectedFilterTag = ({ src, selectedItem,setSelectedItem }: SelectedTagProps) => {
  const removeItem = (itemToRemove: FilterItem) => {
    const updatedItems = selectedItem.filter((item) => item.name !== itemToRemove.name);
    setSelectedItem(updatedItems); 
  };
  return (
    <div className="flex flex-row gap-1">
      {selectedItem
        .slice(1,)
        .map((item) => (
        <div key={item.name} className="w-fit p-4 h-8 border rounded-3xl flex gap-1 items-center justify-center bg-half-light">
          <img src={src} alt={item.name} className="w-4 h-5" />
          <span className=" text-xs md:text-sm lg:text-base">{item.name}</span>
          <IonIcon onClick={()=>removeItem(item)} name="close-outline"></IonIcon>
        </div>
      ))}
    </div>
  );
};
