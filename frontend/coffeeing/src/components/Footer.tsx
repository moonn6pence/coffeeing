import React from "react";

export const Footer = ()=>{
  return(
    <div className="w-full bg-white h-24 border-t-2 ">
      <div className="flex flex-row justify-center">
        {/* CONTACTS */}
        <div className="w-1/4 flex flex-col">
          <p className=" text-lg">CONTACTS</p>
          <p>김한성 : </p>
          <p>신현철 : </p>
          <p>백승윤 :</p>
          <p>김태용 : </p>
          <p>김하늘 : </p>
          <p>김현아 : </p>
        </div>
        {/* 또 뭐 넣지? */}
        <div className="w-1/4 flex flex-col">
          <p>dkdk</p>
          <p>아아</p>
        </div>
      </div>
    </div>
  )
}