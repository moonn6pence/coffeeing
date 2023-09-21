import React, { ChangeEvent, useEffect, useState } from 'react';
import profile from 'assets/막내.png';
import editIcon from 'assets/edit.svg';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

type ProfileProps = {
  nickname: string;
  imgLink: string;
};

export const MyProfile = (props: ProfileProps) => {
  const { nickname, imgLink } = props;
  const [edit, setEdit] = useState(false);
  const [nickChange, setNickChange] = useState(nickname);
  const [existNickname, setExistNickname] = useState(false);
  const [message, setMessage] = useState('이미 존재하는 닉네임입니다');

  // 닉네임 변경상태 받기
  const onChangeNickname = (e: ChangeEvent<HTMLInputElement>) => {
    setNickChange(e.target.value);
  };

  // 닉네임 중복확인
  const checkNickExistense = async () => {
    try {
      const response = await privateRequest.get(
        `${API_URL}/member/unique-nickname`,
        { params: { nickname: nickChange } },
      );

      const responseDate = response.data.data;

      if (responseDate.exist) {
        setExistNickname(true);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (nickname.length > 0 && nickname.length < 11) {
      checkNickExistense;
    }
  }, [nickChange]);

  // 닉네임 변경하기
  const editNickname = () => {
    // if (nickname.length > 0 && !existNickname) {
    //   // 추가로직 필요
    //   privateRequest.put(`${API_URL}/member/nickname`);
    // }
    setEdit(false);
  };

  return (
    <div className="w-72 flex flex-col items-center">
      <img
        src={profile}
        alt="프로필이미지"
        className="w-44 h-44 rounded-full"
      />
      {edit ? (
        <div className="flex flex-col h-7 mt-6">
          <div>
            <input type="text" onChange={onChangeNickname} maxLength={10} />
            <button
              onClick={editNickname}
              className="bg-my-black text-white font-bold text-base px-2 rounded-3xl"
            >
              변경하기
            </button>
          </div>
          {existNickname ? <p className="text-red-600">{message}</p> : ''}
        </div>
      ) : (
        <div className="flex justify-center mt-6">
          <p className="font-medium text-xl mr-2">{nickname}</p>
          <button
            onClick={() => {
              setEdit(true);
            }}
          >
            <img src={editIcon} alt="수정하기" className="w-6 h-6" />
          </button>
        </div>
      )}
    </div>
  );
};
