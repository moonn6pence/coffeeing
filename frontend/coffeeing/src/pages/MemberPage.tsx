import React, { useEffect, useState } from 'react';
import { MyProfile } from 'components/Profile/MyProfile';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

export const MemberPage = () => {
  const [nickname, setNickname] = useState('하이');
  const [imgLink, setImgLink] = useState('');

  // 나중에 연결할 예정
  // useEffect(() => {
  //   privateRequest
  //     .get(`${API_URL}/member/info/1`)
  //     .then((res) => {
  //       setNickname(res.data.data.nicknmae);
  //       setImgLink(res.data.data.profileImage);
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // }, []);
  return (
    <div className="w-300 bg-light h-80 flex items-center">
      <MyProfile nickname={nickname} imgLink={imgLink} />
    </div>
  );
};
