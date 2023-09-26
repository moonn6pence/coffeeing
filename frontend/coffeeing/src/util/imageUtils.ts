import { publicRequest } from './axios';

/**
 *
 * @param imgUrl 이미지의 로컬 dataUrl
 * @param awsUrl AWS 원격 URL
 * @param callback (awsUrl:string, localImageUrl:string)=>unknown 이미지 업로드 후 실행시킬 콜백함수
 */
export const uploadImage = (
  imgUrl: string | ArrayBuffer | null,
  awsUrl: string,
  callback: void | ((awsUrl: string, localImageUrl: string) => unknown),
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
  callback: void | ((awsUrl: string, localImageUrl: string) => unknown),
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
  callback: void | ((awsUrl: string, localImageUrl: string) => unknown),
) => {
  console.log('do stuff like send to s3');

  // upload image to AWS S3
  fetch(localImageUrl)
    .then((response) => response.blob())
    .then((blob) => {
      console.log('Convert url to blob');
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


// ====================================================== // 
