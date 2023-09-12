import React from 'react';
import { NavBarButton } from './NavBarButton';

export const NavBarBody = () => {
  return (
    <div className="w-screen h-16 border-b border-light-roasting flex py-2 px-30">
      <NavBarButton value="로그인" navLink="/login" />
      <NavBarButton value="회원가입" navLink="/signup" />
    </div>
  );
};
