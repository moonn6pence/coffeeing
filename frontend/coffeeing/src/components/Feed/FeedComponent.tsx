import React, { useEffect, useState } from 'react';
import noImage from '../../assets/no_image.png';

type FeedItemProps = {
  feedId: number;
  imageUrl: string;
};

export const FeedComponent = (props: FeedItemProps) => {
  const { feedId, imageUrl } = props;
  const [isImageUsable, setIsImageUsable] = useState<boolean>(false);

  const checkImageAvailable = async (url: string) => {
    const result = await fetch(url);
    const buff = await result.blob();
    if (buff.type.startsWith('image/')) {
      setIsImageUsable(true);
    }
  };

  useEffect(() => {
    checkImageAvailable(imageUrl);
  }, []);

  const openFeed = () => {
    console.log(feedId);
  };

  return (
    <div
      className="image-wrapper border w-[200px] h-[200px] m-2 rounded-lg cursor-pointer flex items-center justify-center"
      onClick={openFeed}
    >
      {isImageUsable ? (
        <img src={imageUrl} alt="이미지" />
      ) : (
        <img src={noImage} alt="이미지 실패" />
      )}
    </div>
  );
};
