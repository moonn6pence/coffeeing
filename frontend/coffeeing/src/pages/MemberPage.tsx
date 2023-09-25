import React, { useEffect, useState } from 'react';
import { MyProfile } from 'components/Profile/MyProfile';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useParams } from 'react-router-dom';

type UserData = {
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

  return (
    <div className="w-300 bg-light h-80 flex items-center">
      {
        userExists?(
          <MyProfile
            id={typeof id === 'string' ? Number.parseInt(id) : undefined}
            nickname={userData.nickname}
            profileImage={userData.profileImage}
          />
        ):(
          <div className='text-center w-screen'>
            <h3>해당 유저가 존재하지 않습니다.</h3>
          </div>
        )
      }
    </div>
  );
};
