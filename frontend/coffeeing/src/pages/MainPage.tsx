import React from "react";
import recImg from "assets/main/추천페이지.png"
import listImg from "assets/main/리스트페이지.png"
import tasteImg from "assets/main/취향분석페이지.png"
import treeImg from "assets/main/커피성장.png"
import { NavBarButton } from "components/NavBar/NavBarButton";

export const MainPage = () => {
  return (
    <div>
      <div className="w-screen h-[750px] bg-light"></div>
      <div className="w-screen h-[750px] flex justify-evenly items-center">
        <img src={recImg} alt="추천페이지" className="w-[579px] h-96" />
        <div>
          <p className="font-bold text-[42px]">취향에 맞는 커피 추천</p>
          <p className="text-xl text-[#7C7C7C]">본인의 취향에 맞는 원두와 캡슐을 찾아보세요</p>
          <NavBarButton value="추천받기" navLink="/recommend-main" />
        </div>
      </div>
      <div className="w-screen h-[750px] bg-light"></div>
      <div className="w-screen h-[750px]"></div>
      <div className="w-screen h-[750px] bg-light"></div>
    </div>
  )
}