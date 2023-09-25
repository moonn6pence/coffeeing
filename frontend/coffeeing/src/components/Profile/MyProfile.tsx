import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import profile from 'assets/막내.png';
import noImage from 'assets/no_image.png';
import editIcon from 'assets/edit.svg';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useDispatch } from 'react-redux';
import { setMyProfileImage } from 'store/memberSlice';
import axios from 'axios';

type ProfileProps = {
  nickname: string;
  imgLink: string | undefined;
};

export const MyProfile = (props: ProfileProps) => {
  const { nickname, imgLink } = props;
  const [edit, setEdit] = useState(false);
  const [nickChange, setNickChange] = useState(nickname);
  const [existNickname, setExistNickname] = useState(false);
  const [message, setMessage] = useState('이미 존재하는 닉네임입니다');
  const imageRef = useRef<HTMLInputElement>(null);
  const [imageRefreshKey, setImageRefreshKey] = useState(Date.now());
  const dispatch = useDispatch();

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

  // Image onClick handler
  const handleProfileImageChangeClick = () => {
    if (imageRef.current) {
      imageRef.current.click();
    }
  };

  // image change entrypoint onInput handler
  const handleImageOnInput = () => {
    console.log('Image change go!');
    const reader: FileReader = new FileReader();
    reader.addEventListener('load', async () => {
      console.log('go conversion!');
      if (imgLink) {
        createNewImage(reader.result, imgLink, callbackProfileImageUpload);
      } else {
        // generate new AWS S3 image url
        const awsData = await privateRequest.get(`${API_URL}/aws/img`);
        const awsS3Urls = awsData.data.data;
        console.log(awsS3Urls);
        const imageUrl = awsS3Urls.imageUrl;
        createNewImage(reader.result, imageUrl, callbackProfileImageUpload);
      }
    });
    if (imageRef.current?.files) {
      console.log('image file exists');
      const file = imageRef.current.files[0];
      if (file) {
        reader.readAsDataURL(file);
      }
    }
  };

  const createNewImage = (
    imgUrl: string | ArrayBuffer | null,
    awsUrl: string,
    callback: void | ((awsUrl: string, localImageUrl: string) => any),
  ) => {
    console.log('converttowebp');
    const img = new Image();
    if (typeof imgUrl === 'string') {
      img.src = imgUrl;
    }
    if (img.complete) {
      convertToWebp(img, awsUrl, callback);
    } else {
      img.onload = () => {
        convertToWebp(img, awsUrl, callback);
      };
    }
  };

  // 이미지를 Webp 형식으로 변경
  const convertToWebp = (
    img: HTMLImageElement,
    awsUrl: string,
    callback: void | ((awsUrl: string, localImageUrl: string) => any),
  ) => {
    const canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;
    const ctx = canvas.getContext('2d');
    ctx?.drawImage(img, 0, 0);
    const localImageUrl = canvas.toDataURL('image/webp');
    console.log(localImageUrl);
    sendToS3(localImageUrl, awsUrl, callback);
  };

  const sendToS3 = async (
    localImageUrl: string,
    awsUrl: string,
    callback: void | ((awsUrl: string, localImageUrl: string) => any),
  ) => {
    console.log('do stuff like send to s3');

    // upload image to AWS S3
    fetch(localImageUrl)
      .then((response) => response.blob())
      .then((blob) => {
        console.log("Convert url to blob");
        const imageFile = new File([blob], 'local-webp.webp', {
          type: blob.type,
        });
        console.log(awsUrl);
        return publicRequest.put(
          // presignedUrl,
          awsUrl,
          imageFile,
        );
      })
      .then((imageUploadResponse) => {
        console.log(imageUploadResponse);
        console.log(callback);
        if (callback) {
          callback(awsUrl, localImageUrl);
        }
      });
  };

  const callbackProfileImageUpload = (aws: string, _local: string) => {
    console.log("Callback called!!");
    dispatch(setMyProfileImage(aws));
    privateRequest.put(`${API_URL}/member/profile`, { profileImageUrl: aws });
    setImageRefreshKey(Date.now());
  };

  return (
    <div className="w-72 flex flex-col items-center">
      <div
        className="img-wrapper rounded-full hover:cursor-pointer"
        onClick={() => handleProfileImageChangeClick()}
      >
        {imgLink ? (
          <img
            src={imgLink}
            key={imageRefreshKey}
            alt="프로필이미지"
            className="w-44 h-44 rounded-full border-2"
          />
        ) : (
          <img
            src={noImage}
            alt="프로필이미지"
            className="w-44 h-44 rounded-full border-2"
          />
        )}
      </div>
      <input
        type="file"
        name="profile"
        id="profile"
        accept="image/png, image/jpeg, image/jpg"
        ref={imageRef}
        className="collapse"
        onInput={() => {
          handleImageOnInput();
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
