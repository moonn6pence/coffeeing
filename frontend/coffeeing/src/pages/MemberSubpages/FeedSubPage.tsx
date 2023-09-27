import { isAxiosError } from 'axios';
import { FeedItem } from 'components/Feed/FeedItem';
import { MemberId } from 'pages/MemberPage';
import React, { useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useOutletContext } from 'react-router-dom';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

type FeedItem = {
  feedId: number;
  imageUrl: string;
};

export const FeedSubPage = () => {
  const { id: memberId } = useOutletContext<MemberId>();
  const FEED_SIZE = 12;
  const [cursor, setCursor] = useState<undefined | number>(undefined);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [feedList, setFeedList] = useState<Array<JSX.Element>>([]);

  const queryFeeds = async () => {
    return await privateRequest
      .get(`${API_URL}/feeds/${memberId}/list`, {
        params: {
          cursor: cursor,
          memberId: memberId,
          size: FEED_SIZE,
        },
      })
      .then((res) => res.data.data)
      .catch((error) => {
        if (!isAxiosError(error)) {
          console.error('[GET feeds by MemberId failed]');
        }
      });
  };
  const loadFeeds = async () => {
    const data = await queryFeeds();
    if (data) {
      setFeedList((ori) => {
        return [
          ...ori,
          ...data.feed.map((item: FeedItem) => {
            return (
              <FeedItem
                feedId={item.feedId}
                imageUrl={item.imageUrl}
                key={item.feedId}
              />
            );
          }),
        ];
      });
    }
    setHasMore(data.hasNext);
    setCursor(data.nextCursor);
  };

  return (
    <div className="sub-wrapper">
      <div className="feed-wrapper">
        <InfiniteScroll
          dataLength={FEED_SIZE}
          next={loadFeeds}
          hasMore={hasMore}
          loader={
            <h4>Loading...</h4>
          }
        >
          {feedList}
        </InfiniteScroll>
      </div>
    </div>
  );
};
