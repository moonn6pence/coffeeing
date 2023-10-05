import React from "react";
import { Menu } from "@headlessui/react";
import IonIcon from "@reacticons/ionicons";

const navigation = [
  { name: '원두/캡슐', href: '/beans' },
  { name: '추천', href: '/recommend-main' },
  { name: '피드', href: '/feeds' },
  { name: '검색', href: '/search' },
];

export const NavBarDropdown = () => {
  return (
    <div className="md:hidden">
      <Menu as='div' className="relative inline-block">
        <Menu.Button className="p-2 w-fit">
          <IonIcon size="large" name="menu-outline"></IonIcon>
        </Menu.Button>
        <Menu.Items className="absolute flex flex-col right-0 mt-2 w-56 divide-y z-20 border shadow-md">
        {/* <Menu.Items className="flex flex-col"> */}
          {navigation.map((link) => (
            <Menu.Item key={link.href}>
              {({ active }) => (
                <a
                  href={link.href}
                  className={`
                    ${active ? 'bg-white text-light-roasting' : 'bg-white text-black'}
                    p-3 text-center
                  `}
                >
                  {link.name}
                </a>
              )}
            </Menu.Item>
          ))}
        </Menu.Items>
      </Menu>
    </div>
  );
};
