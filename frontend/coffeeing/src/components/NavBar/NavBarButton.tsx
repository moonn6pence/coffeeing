import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { logout } from 'store/memberSlice';

interface INavButtonProps {
  value: string;
  navLink: string;
  isLogout?: boolean;
  dark?: boolean;
}

export const NavBarButton = ({
  value,
  navLink,
  isLogout = false,
  dark = false,
}: INavButtonProps) => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const goLink = () => {
    if (isLogout) {
      // TODO LOGOUT API
      dispatch(logout());
    }
    navigate(`${navLink}`);
  };

  const divCommon =
    'px-4 py-3 rounded-3xl hover:brightness-90 cursor-pointer w-max';
  const pCommon = 'text-base text-light-roasting font-bold';

  return (
    <div
      className={dark ? `${divCommon} bg-my-black` : `${divCommon} bg-light`}
      onClick={goLink}
    >
      <p className={`${dark ? `${pCommon} text-white` : `${pCommon}`} text-xs md:text-base`}>{value}</p>
    </div>
  );
};
