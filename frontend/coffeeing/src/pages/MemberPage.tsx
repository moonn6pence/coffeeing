import React, { useEffect, useState } from 'react';
import { MyProfile } from 'components/Profile/MyProfile';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { NavLink, Outlet, useParams } from 'react-router-dom';

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
      <div className="wrapper w-full flex items-center flex-col">
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
        <div className="sub-section w-300 mt-12">
          <nav>
            <NavLink to={`/member/${id}`}>
              경험치
            </NavLink>
            <NavLink to={`/member/${id}/bookmark`}>북마크</NavLink>
            <NavLink to={`/member/${id}/feed`}>피드</NavLink>
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
