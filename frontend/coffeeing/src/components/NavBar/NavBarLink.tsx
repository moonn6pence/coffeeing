import classNames from 'classnames';
import React from 'react';
import { useNavigate } from 'react-router-dom';

interface ILinkProps {
  name: string;
  link: string;
  active: boolean;
}

export const NavBarLink = (props: ILinkProps) => {
  const navigate = useNavigate();
  const { name, link, active } = props;
  // 공통 CSS
  const commonClass = 'h-12 font-bold text-base hover:brightness-125';
  // active 상태에 따른 CSS
  const linkClass = active
    ? `${commonClass} text-cinamon-roasting`
    : `${commonClass} text-light-roasting`;

  const goLink = () => {
      navigate(`${link}`);
  };
  
  
  return (
    <div
      className={
        classNames(
          'cursor-pointer h-12 font-bold text-base hover:brightness-125 text-light-roasting', 
          active ? 'border-b-2 border-cinamon-roasting py-3' : 'py-3'
        )
      }
      onClick={goLink}
    >
      {name}
    </div>
  );
};
