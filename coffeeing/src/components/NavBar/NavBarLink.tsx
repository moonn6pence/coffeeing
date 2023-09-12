import React from 'react';

interface ILinkProps {
  name: string;
  link: string;
  active: boolean;
}

export const NavBarLink = (props: ILinkProps) => {
  const { name, link, active } = props;

  return (
    <div>
      <a href={link} className={active ? 'text-cinamon-roasting' : ''}>
        {name}
      </a>
    </div>
  );
};
