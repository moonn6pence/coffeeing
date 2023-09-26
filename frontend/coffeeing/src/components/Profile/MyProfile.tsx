import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import noImage from 'assets/no_image.png';
import editIcon from 'assets/edit.svg';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useDispatch, useSelector } from 'react-redux';
import { setMyProfileImage } from 'store/memberSlice';
import { RootState } from 'store/store';
import { uploadImage } from 'util/imageUtils';

type ProfileProps = {
  id: number | undefined;
  nickname: string;
  profileImage: string;
};

export const MyProfile = (props: ProfileProps) => {
  const { id,nickname,profileImage } = props;
  const { memberId } = useSelector(
    (state: RootState) => state.member,
  );

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
      if (profileImage) {
        uploadImage(reader.result, profileImage, callbackProfileImageUpload);
      } else {
        // generate new AWS S3 image url
        const awsData = await privateRequest.get(`${API_URL}/aws/img`);
        const awsS3Urls = awsData.data.data;
        console.log(awsS3Urls);
        const imageUrl = awsS3Urls.imageUrl;
        uploadImage(reader.result, imageUrl, callbackProfileImageUpload);
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
  
  /**
   * 
   * @param aws aws주소
   * @param _local 데이터의 localUrl 주소
   */
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const callbackProfileImageUpload = async (aws: string, _local: string) => {
    console.log('Callback called!!');
    dispatch(setMyProfileImage(aws));
    await privateRequest.put(`${API_URL}/member/profile`, { profileImageUrl: aws });
    if(profileImage){
      setImageRefreshKey(Date.now());
    }else{
      window.location.reload();
    }
  };

  return (
    <div className="w-72 flex flex-col items-center">
      <div
        className={`img-wrapper rounded-full ${
          memberId === id ? 'hover:cursor-pointer' : ''
        }`}
        onClick={() => {
          if (memberId === id) {
            handleProfileImageChangeClick();
          }
        }}
      >
        {profileImage ? (
          <img
            src={profileImage}
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
