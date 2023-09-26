import React, { ChangeEvent, useRef, useState } from 'react';
import noImage from 'assets/no_image.png';
import editIcon from 'assets/edit.svg';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useDispatch, useSelector } from 'react-redux';
import { setMyProfileImage } from 'store/memberSlice';
import { RootState } from 'store/store';
import { uploadImage } from 'util/imageUtils';
import { ValidateNicknameResult, validateNickname } from 'util/regexUtils';
import { UserData } from 'pages/MemberPage';

type ProfileProps = {
  id: number | undefined;
  nickname: string;
  profileImage: string;
  setters:ProfileSetters
};

type ProfileSetters ={
  setUserData:React.Dispatch<React.SetStateAction<UserData>>
}

export const MyProfile = (props: ProfileProps) => {
  const { id, nickname, profileImage, setters } = props;
  const { memberId } = useSelector((state: RootState) => state.member);
  const [nicknameInput, setNicknameInput] = useState('');
  const [edit, setEdit] = useState(false);
  const [nicknameUseable, setNicknameUsable] = useState(false);
  const [message, setMessage] = useState('');
  const imageRef = useRef<HTMLInputElement>(null);
  const [imageRefreshKey, setImageRefreshKey] = useState(Date.now());
  const dispatch = useDispatch();

  // 닉네임 변경상태 받기
  const onChangeNickname = async (e: ChangeEvent<HTMLInputElement>) => {
    const nicknameCandidate = e.target.value;
    // check valid format
    const validCheckResult = validateNickname(nicknameCandidate);
    if (!validCheckResult.isValid) {
      console.log('invalid by regex');
      setMessage(validCheckResult.message);
      setNicknameUsable(false);
      return;
    }
    // check duplicate
    const duplicateCheckResult = await checkNickExist(nicknameCandidate);
    if (!duplicateCheckResult.isValid) {
      setMessage(duplicateCheckResult.message);
      setNicknameUsable(false);
      return;
    }

    setMessage('');
    setNicknameInput(nicknameCandidate);
    setNicknameUsable(true);
  };

  // 닉네임 중복확인
  const checkNickExist = async (
    nicknameCandidate: string,
  ): Promise<ValidateNicknameResult> => {
    try {
      const response = await privateRequest.get(
        `${API_URL}/member/unique-nickname`,
        { params: { nickname: nicknameCandidate } },
      );
      const responseDate = response.data.data;

      if (responseDate.exist) {
        return {
          isValid: false,
          message: '사용중인 닉네임입니다.',
        };
      }
      return {
        isValid: true,
        message: '사용가능한 닉네임입니다.',
      };
    } catch (error) {
      console.log(error);
      return {
        isValid: false,
        message: '서버에 문제가 생겨 조회가 불가능합니다.',
      };
    }
  };

  // 닉네임 변경하기
  const editNickname = async () => {
    const data = { nickname: nicknameInput };
    const result = await privateRequest.put(`${API_URL}/member/nickname`, data);
    if (result.data.code === '200') {
      setEdit(false);
      setters.setUserData({
        profileImage:profileImage,
        nickname:nicknameInput
      })
    } else {
      alert('닉네임 변경에 실패했습니다!');
    }
  };
  // --------------------------------- PROFILE IMAGE ------------------------------- //
  // Image onClick handler
  const handleProfileImageChangeClick = () => {
    if (imageRef.current) {
      imageRef.current.click();
    }
  };

  // image change entrypoint onInput handler
  const handleImageOnInput = () => {
    const reader: FileReader = new FileReader();
    reader.addEventListener('load', async () => {
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
    await privateRequest.put(`${API_URL}/member/profile`, {
      profileImageUrl: aws,
    });
    if (profileImage) {
      setImageRefreshKey(Date.now());
    } else {
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
              disabled={!nicknameUseable}
            >
              변경하기
            </button>
            <button
              onClick={() => setEdit(false)}
              className="bg-my-black text-white font-bold text-base px-2 rounded-3xl"
            >
              취소하기
            </button>
          </div>
          {!nicknameUseable ? <p className="text-red-600">{message}</p> : ''}
        </div>
      ) : (
        <div className="flex justify-center mt-6">
          <p className="font-medium text-xl mr-2">{nickname}</p>
          {memberId === id ? (
            <button
              onClick={() => {
                setEdit(true);
              }}
            >
              <img src={editIcon} alt="수정하기" className="w-6 h-6" />
            </button>
          ) : (
            ''
          )}
        </div>
      )}
    </div>
  );
};
