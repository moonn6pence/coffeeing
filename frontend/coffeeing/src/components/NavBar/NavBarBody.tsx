import React from 'react';
import { NavBarButton } from './NavBarButton';
import { NavBarLinkList } from './NavBarLinkList';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import profile from 'assets/profile.svg';
import { useNavigate } from 'react-router-dom';

export const NavBarBody = () => {
  const isLogin = useSelector((state: RootState) => state.member.isLogin);
  const navigate = useNavigate();
  return (
    <div className="w-screen h-16 border-b border-light-roasting flex justify-around py-2 px-30">
      <NavBarLinkList />
      {isLogin ? (
        <div className="flex space-x-3">
          <img
            src={profile}
            className="w-9 h-9 cursor-pointer my-auto"
            onClick={() => {
              navigate('/myprofile');
            }}
          />
          <NavBarButton value="로그아웃" navLink="/logout" />
        </div>
      ) : (
        <div className="flex space-x-3">
          <NavBarButton value="로그인" navLink="/login" />
          <NavBarButton value="회원가입" navLink="/signup" />
        </div>
      )}
    </div>
  );
};
