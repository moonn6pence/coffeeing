import React from 'react';

interface ILinkProps {
  name: string;
  link: string;
  active: boolean;
}

export const NavBarLink = (props: ILinkProps) => {
  const { name, link, active } = props;
  // 공통 CSS
  const commonClass = 'h-12 font-bold text-base hover:brightness-125';
  // active 상태에 따른 CSS
  const linkClass = active
    ? `${commonClass} text-cinamon-roasting`
    : `${commonClass} text-light-roasting`;

  return (
    <div
      className={active ? 'border-b-2 border-cinamon-roasting py-3' : 'py-3'}
    >
      <a href={link} className={linkClass}>
        {name}
      </a>
    </div>
  );
};
