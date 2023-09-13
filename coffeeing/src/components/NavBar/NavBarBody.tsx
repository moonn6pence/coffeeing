import React from 'react';
import { NavBarButton } from './NavBarButton';
import { NavBarLinkList } from './NavBarLinkList';

export const NavBarBody = () => {
  return (
    <div className="w-screen h-16 border-b border-light-roasting flex justify-around py-2 px-30">
      <NavBarLinkList />
      <div className="flex space-x-3">
        <NavBarButton value="로그인" navLink="/login" />
        <NavBarButton value="회원가입" navLink="/signup" />
      </div>
    </div>
  );
};
