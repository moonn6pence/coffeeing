import React from 'react';

type FeedItemProps = {
  feedId: number;
  imageUrl: string;
};

export const FeedItem = (props: FeedItemProps) => {
  const { feedId, imageUrl } = props;


  return (
    <div>
      <div className="image-wrapper">
        <img src={imageUrl} alt="이미지" />
      </div>
    </div>
  );
};
