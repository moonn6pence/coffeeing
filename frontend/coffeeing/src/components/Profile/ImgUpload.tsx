import React, { useMemo, useRef, useState } from 'react';

type UploadImg = {
  file: File;
  thumbnail: string;
  type: string;
};

export const ImgUpload = () => {
  // 올린 파일 경로
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  // 프로필 이미지 state
  const [profileImg, setProfileImg] = useState<UploadImg | null>(null);
  // 버튼이 눌리면 파일 업로드 창 띄우기
  const fileInput = () => {
    fileInputRef.current?.click();
  };

  // 프로필 이미지 미리보기 부분
  const ShowImg = useMemo(() => {
    if (!profileImg && profileImg == null) {
      return;
    }
  }, [profileImg]);
};
