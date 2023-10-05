import React, { useEffect, useState } from 'react';
import { MemberProfile } from 'components/Profile/MemberProfile';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { Outlet, useParams } from 'react-router-dom';
import { NavLinkWrapper } from 'components/NavLink/NavLinkWrapper';
import { NavBarButton } from 'components/NavBar/NavBarButton';
import { CoffeeCriteria } from 'service/member/types';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';

export type UserData = {
  nickname: string;
  profileImage: string;
  preference: CoffeeCriteria | null;
};

export type MemberId = {
  id: number;
};

export const MemberPage = () => {
  const { id } = useParams();
  const userId = id ? parseInt(id, 10) : undefined;
  const [userData, setUserData] = useState<UserData>({
    nickname: '',
    profileImage: '',
    preference: null,
  });
  const [userExists, setUserExists] = useState(false);
  const isLogin = useSelector((state: RootState) => state.member.isLogin);
  const myId = useSelector((state: RootState) => state.member.memberId);

  useEffect(() => {
    // get user data
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
  }, [id]);

  if (userExists) {
    return (
      <div className="wrapper w-4/5 flex items-stretch flex-col m-auto mt-10 mb-20">
        <div className="w-full bg-light h-fit flex items-center">
          <MemberProfile
            id={typeof id === 'string' ? Number.parseInt(id) : undefined}
            nickname={userData.nickname}
            profileImage={userData.profileImage}
            preference={userData.preference}
            setters={{
              setUserData: setUserData,
            }}
          />
        </div>
        <div className="sub-section mt-12 grow">
          <nav className="h-45 space-x-4">
            <NavLinkWrapper to={`/member/${id}`} text="경험치" end />
            <NavLinkWrapper to={`/member/${id}/bookmark`} text="북마크" />
            <NavLinkWrapper to={`/member/${id}/feed`} text="피드" />
          </nav>
          <Outlet context={{ id }} />
        </div>
        {isLogin && userId == myId ? (
          <div className="spacer flex justify-center pt-10">
            <NavBarButton value="로그아웃" navLink="/" isLogout={true} />
          </div>
        ) : null}
      </div>
    );
  } else {
    return (
      <div className="flex justify-center">
        <div className="w-4/5 mt-10 bg-light h-80 flex items-center">
          <div className="text-center w-screen">
            <h3>해당 유저가 존재하지 않습니다.</h3>
          </div>
        </div>
      </div>
    );
  }
};
