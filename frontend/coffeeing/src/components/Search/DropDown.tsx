import React, { Dispatch, SetStateAction } from "react";

type DropDownProps = {
  data:{
    label:string;
    num:number;
    roast:boolean;
    setRoast: Dispatch<SetStateAction<boolean>>;
  }[],
  handleFilter: (num:number, roast:boolean, setRoast:Dispatch<SetStateAction<boolean>>)=>void;
}

export const DropDown = ({data, handleFilter}:DropDownProps)=>{
  return(
    <span className="w-36 flex flex-col border rounded-xl mt-2">
      {data.map((item) => {
        const { label, num, roast, setRoast } = item;
        return (
          <span
            className={`w-full h-10 text-center align-middle ${roast ? 'bg-drop-down' : 'bg-white'}`}
            onClick={() => handleFilter(num, roast, setRoast)}
            key={num}
          >
            {label}
          </span>
        );
      })}
    </span>
  )
}