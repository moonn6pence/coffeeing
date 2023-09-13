import React from 'react';
import { useNavigate } from 'react-router-dom';

interface INavButtonProps {
  value: string;
  navLink: string;
}

export const NavBarButton = (props: INavButtonProps) => {
  const { value, navLink } = props;
  const navigate = useNavigate();
  const goLink = () => {
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
