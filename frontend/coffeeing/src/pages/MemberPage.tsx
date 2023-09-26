import React, { useEffect, useState } from 'react';
import { MyProfile } from 'components/Profile/MyProfile';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { Outlet, useParams } from 'react-router-dom';
import { NavLinkWrapper } from 'components/NavLink/NavLinkWrapper';

export type UserData = {
  nickname: string;
  profileImage: string;
};

export const MemberPage = () => {
  const { id } = useParams();
  const [userData, setUserData] = useState<UserData>({
    nickname: '',
    profileImage: '',
  });
  const [userExists, setUserExists] = useState(false);

  useEffect(() => {
    privateRequest
      .get(`${API_URL}/member/info/${id}`)
      .then(({ data }) => {
        console.log(data);
        setUserData(data.data);
        setUserExists(true);
      })
      .catch((error) => {
        console.log(error);
        setUserExists(false);
      });
  }, []);

  if (userExists) {
    return (
      <div className="wrapper w-full flex items-stretch flex-col w-96">
        <div className="w-300 bg-light h-80 flex items-center">
          <MyProfile
            id={typeof id === 'string' ? Number.parseInt(id) : undefined}
            nickname={userData.nickname}
            profileImage={userData.profileImage}
            setters={{
              setUserData: setUserData,
            }}
          />
        </div>
        <div className="sub-section mt-12 grow">
          <nav className="h-45">
            <NavLinkWrapper to={`/member/${id}`} text="경험치" end />
            <NavLinkWrapper to={`/member/${id}/bookmark`} text="북마크" />
            <NavLinkWrapper to={`/member/${id}/feed`} text="피드" />
          </nav>
          <Outlet />
        </div>
      </div>
    );
  } else {
    return (
      <div className="flex justify-center">
        <div className="w-300 bg-light h-80 flex items-center">
          <div className="text-center w-screen">
            <h3>해당 유저가 존재하지 않습니다.</h3>
          </div>
        </div>
      </div>
    );
  }
};
