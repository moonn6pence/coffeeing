import React from 'react';
import { NavBarLink } from './NavBarLink';
import { useLocation } from 'react-router-dom';

export const NavBarLinkList = () => {
  // 현재 위치
  const { pathname } = useLocation();

  const navigation = [
    { name: '원두/캡슐', href: '/beans' },
    { name: '추천', href: '/recommend' },
    { name: '피드', href: '/feed' },
    { name: '검색', href: '/search' },
  ];

  return (
    <div className="flex space-x-16">
      {navigation.map((page) => (
        <NavBarLink
          name={page.name}
          link={page.href}
          active={pathname === page.href}
          key={page.name}
        />
      ))}
    </div>
  );
};
