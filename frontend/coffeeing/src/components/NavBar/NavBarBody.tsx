import React from 'react';
import { NavBarButton } from './NavBarButton';
import { NavBarLinkList } from './NavBarLinkList';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import profile from 'assets/profile.svg';
import logo from 'assets/logo.svg';
import { useNavigate } from 'react-router-dom';
import { NavBarDropdown } from './NavBarDropdown';
export const NavBarBody = () => {
  const isLogin = useSelector((state: RootState) => state.member.isLogin);
  const myInfo = useSelector((state:RootState) => state.member);
  const navigate = useNavigate();
  return (
    <div className="w-screen h-16 border-b border-light-roasting py-2 flex justify-center">
      <div className="flex justify-between w-4/5">
        <button
          onClick={() => {
            navigate('/');
          }}
        >
          <img src={logo} alt="로고" />
        </button>
        <NavBarLinkList />
        {isLogin ? (
          <div className="flex space-x-3">
            <img
              src={profile}
              className="w-9 h-9 cursor-pointer my-auto"
              onClick={() => {
                if(myInfo){
                  navigate(`/member/${myInfo.memberId}`);
                }
              }}
            />
          </div>
        ) : (
          <div className="flex space-x-3">
            <NavBarButton value="로그인" navLink="/login" />
            <NavBarButton value="회원가입" navLink="/signup" />
          </div>
        )}
        <NavBarDropdown/>
      </div>
    </div>
  );
};
