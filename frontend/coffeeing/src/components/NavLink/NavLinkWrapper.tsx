import React from 'react';
import { NavLink } from 'react-router-dom';

type NavLinkWrapperProps = {
  to: string;
  text: string;
  end?: boolean; // exact
};

export const NavLinkWrapper = (props: NavLinkWrapperProps) => {
  const { to, text, end } = props;
  return (
    <NavLink
      draggable={false}
      end={Boolean(end)}
      to={to}
      className={({ isActive }) => {
        const defaultClassName =
          'p-5 inline-block w-36 rounded-t-[10px] text-center font-bold text-xl ';
        return defaultClassName + (isActive ? 'bg-light' : 'bg-light-roasting text-white');
      }}
    >
      {text}
    </NavLink>
  );
};
