import { isAxiosError } from 'axios';
import { FeedComponent } from 'components/Feed/FeedComponent';
import { MemberId } from 'pages/MemberPage';
import React, { useCallback, useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useOutletContext } from 'react-router-dom';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

type FeedItem = {
  feedId: number;
  imageUrl: string;
};

type FeedItemResponse = {
  feedId: number;
  images: Array<string>;
};

export const FeedSubPage = () => {
  const { id: memberId } = useOutletContext<MemberId>();
  const FEED_SIZE = 12;
  const [cursor, setCursor] = useState<undefined | number>(undefined);
  const [hasMore, setHasMore] = useState<boolean>(false);
  const [feedList, setFeedList] = useState<Array<FeedItem>>([]);

  const feedComponents = useCallback(() => {
    return feedList.map((item: FeedItem) => {
      return (
        <FeedComponent
          feedId={item.feedId}
          key={item.feedId}
          imageUrl={item.imageUrl}
        />
      );
    });
  }, [feedList]);

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
          ...data.feeds.map((item: FeedItemResponse) => {
            return {
              feedId: item.feedId,
              imageUrl: item.images[0],
            };
          }),
        ];
      });
    }
    setHasMore(data.hasNext);
    setCursor(data.nextCursor);
  };

  useEffect(() => {
    loadFeeds();
  });

  return (
    <div className="sub-wrapper">
      <div className="feed-wrapper">
        <InfiniteScroll
          dataLength={feedList.length}
          next={loadFeeds}
          hasMore={hasMore}
          loader={<h4>Loading...</h4>}
        >
          {feedComponents()}
        </InfiniteScroll>
      </div>
    </div>
  );
};
