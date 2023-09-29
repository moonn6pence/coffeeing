import React from 'react';
import recImg from 'assets/main/추천페이지.png';
import listImg from 'assets/main/리스트페이지.png';
import tasteImg from 'assets/main/취향분석페이지.png';
import treeImg from 'assets/main/커피성장.png';
import { NavBarButton } from 'components/NavBar/NavBarButton';

export const MainPage = () => {
  return (
    <div>
      <div className="w-screen h-[620px] bg-main-page bg-cover flex justify-center">
        <div className="flex flex-col items-center mt-20 h-[210px] justify-between">
          <p className="text-6xl font-bold text-[#4A2F23]">취향에 맞는 커피 추천</p>
          <p className="text-lg text-[#784C3A]">본인의 취향에 맞는 원두와 캡슐을 찾아보세요</p>
          <NavBarButton value="추천받기" navLink="/recommend-main" />
        </div>
      </div>
      <div className="w-screen h-[750px] bg-light flex justify-evenly items-center">
        <div className="space-y-8">
          <p className="font-bold text-[42px] leading-[50px]">
            인기 커피와
            <br />
            키워드 기반 큐레이션
          </p>
          <p className="text-xl text-[#7C7C7C]">
            지금 인기 있는 커피와
            <br />
            키워드 기반의 추천 커피를 만나보세요
          </p>
          <NavBarButton
            value="보러가기"
            navLink="/recommend-main"
            dark={true}
          />
        </div>
        <img src={listImg} alt="리스트페이지" className="w-96 h-[590px]" />
      </div>
      <div className="w-screen h-[750px] flex justify-evenly items-center">
        <img src={tasteImg} alt="취향분석" />
        <div className="space-y-8">
          <p className="font-bold text-[42px] leading-[50px]">
            나만의 커피 취향
            <br />
            분석하기
          </p>
          <p className="text-xl text-[#7C7C7C]">
            본인의 커피 취향
            <br />
            분석 결과를 확인해보세요
          </p>
          <NavBarButton value="내 취향 보러가기" navLink="/recommend-main" />
        </div>
      </div>
      <div className="w-screen h-[750px] bg-light flex justify-evenly items-center">
        <div className="space-y-8">
          <p className="font-bold text-[42px] leading-[50px]">
            나만의
            <br />
            커피 나무 키우기
          </p>
          <p className="text-xl text-[#7C7C7C]">
            Coffeeing 사이트를 이용하고
            <br />
            나만의 커피 나무를 키워보세요
          </p>
          <NavBarButton
            value="키우러가기"
            navLink="/recommend-main"
            dark={true}
          />
        </div>
        <img src={treeImg} alt="커피성장" />
      </div>
    </div>
  );
};
