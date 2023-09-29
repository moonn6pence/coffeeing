import React from 'react';
import { useNavigate } from 'react-router-dom';

interface ILinkProps {
  name: string;
  link: string;
  active: boolean;
}

export const NavBarLink = (props: ILinkProps) => {
  const { name, link, active } = props;
  const navigate = useNavigate();

  // 공통 CSS
  const commonClass = 'h-12 font-bold text-base hover:brightness-125 cursor-pointer';
  // active 상태에 따른 CSS
  const linkClass = active
    ? `${commonClass} text-cinamon-roasting`
    : `${commonClass} text-light-roasting`;

  return (
    <div
      className={active ? 'border-b-2 border-cinamon-roasting py-3' : 'py-3'}
    >
      <span onClick={()=>{navigate(link)}} className={linkClass}>
        {name}
      </span>
    </div>
  );
};
