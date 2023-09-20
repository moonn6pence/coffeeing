import React, { useState } from 'react';
import profile from 'assets/막내.png';
import editIcon from 'assets/edit.svg';

type ProfileProps = {
  nickname: string;
  imgLink: string;
};

export const MyProfile = (props: ProfileProps) => {
  const { nickname, imgLink } = props;
  const [edit, setEdit] = useState(false);

  return (
    <div className="w-44">
      <img
        src={profile}
        alt="프로필이미지"
        className="w-44 h-44 rounded-full"
      />
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
    </div>
  );
};
