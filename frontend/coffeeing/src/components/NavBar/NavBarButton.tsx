import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';
import { logout } from "store/memberSlice";

interface INavButtonProps {
  value: string;
  navLink: string;
  isLogout?: boolean
}

export const NavBarButton = ({ value, navLink, isLogout=false }: INavButtonProps) => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const goLink = () => {
    if(isLogout) {
      // TODO LOGOUT API 
      dispatch(logout());
    }
    navigate(`${navLink}`);
  };

  return (
    <div
      className="px-22px py-3 rounded-3xl bg-light hover:brightness-90 cursor-pointer"
      onClick={goLink}
    >
      <p className="text-base text-light-roasting font-bold">{value}</p>
    </div>
  );
};
