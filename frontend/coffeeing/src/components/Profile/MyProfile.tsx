import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
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
  const [profileImage, setProfileImg] = useState(undefined);
  const imageRef = useRef<HTMLInputElement>(null);

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

  // 이미지 변경하기
  const changeProfileImage = () => {
    if (imageRef.current) {
      console.log('am i wokring?');
      imageRef.current.click();
    }
  };

  const launchImageChange = () => {
    console.log('Image change go!');
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      console.log('go conversion!');
      convertToWebp(reader.result, sendToS3);
    });
    if (imageRef.current?.files) {
      console.log('image file exists');
      const file = imageRef.current.files[0];
      if (file) {
        reader.readAsDataURL(file);
      }
    }
  };

  const convertToWebp = (
    imgUrl: string | ArrayBuffer | null,
    callback: any,
  ) => {
    console.log("converttowebp");
    const img = new Image();
    img.onload = () => { // 여기가 실행 안됨
      console.log('throwing random bullshit');
      const canvas = document.createElement('canvas');
      canvas.width = img.width;
      canvas.height = img.height;
      const ctx = canvas.getContext('2d');
      ctx?.drawImage(img, 0, 0);
      const webpDataUrl = canvas.toDataURL('image/webp');
      callback(webpDataUrl);
    };
  };

  const sendToS3 = (imageUrl: string) => {
    console.log('do stuff like send to s3');

  };

  return (
    <div className="w-72 flex flex-col items-center">
      <div
        className="img-wrapper rounded-full hover:cursor-pointer"
        onClick={() => changeProfileImage()}
      >
        <img
          src={profile}
          alt="프로필이미지"
          className="w-44 h-44 rounded-full"
        />
      </div>
      <input
        type="file"
        name="profile"
        id="profile"
        accept="image/png, image/jpeg, image/jpg"
        ref={imageRef}
        className="collapse"
        onInput={() => {
          console.log('CATCH!');
          launchImageChange();
        }}
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
