import { isAxiosError } from 'axios';
import { FeedComponent } from 'components/Feed/FeedComponent';
import { MemberFeedModal } from 'components/Modal/MemberFeedModal';
import { MemberId } from 'pages/MemberPage';
import React, { useCallback, useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useOutletContext } from 'react-router-dom';
import { FeedDetail } from 'service/feed/types';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

type FeedItem = {
  feedId: number;
  imageUrl: string;
};

type FeedItemResponse = {
  feedId: number;
  images: Array<ImageItem>;
};
type ImageItem = {
  imageUrl: string;
};

export const FeedSubPage = () => {
  const { id: memberId } = useOutletContext<MemberId>();
  const FEED_SIZE = 12;
  const [cursor, setCursor] = useState<undefined | number>(undefined);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [feedList, setFeedList] = useState<Array<FeedItem>>([]);
  const [feedSet, setFeedSet] = useState<Set<number>>(new Set());
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const [feedDetail, setFeedDetail] = useState<FeedDetail | null>(null);

  const feedComponents = useCallback(() => {
    return feedList.map((item: FeedItem) => {
      return (
        <FeedComponent
          feedId={item.feedId}
          key={item.feedId}
          imageUrl={item.imageUrl}
          setters={{
            setIsModalOpen: setIsModalOpen,
            setFeedDetail: setFeedDetail,
          }}
        />
      );
    });
  }, [feedList]);

  const queryFeeds = async () => {
    return await privateRequest
      .get(`${API_URL}/feeds/list/${memberId}`, {
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
    // console.log('data called ', data);
    if (data) {
      const newFeeds = data.feeds.filter((item: FeedItemResponse) => {
        if (!feedSet.has(item.feedId)) {
          setFeedSet((originalSet) => {
            return originalSet.add(item.feedId);
          });
          return true;
        }
        return false;
      });
      setFeedList((ori) => {
        return [
          ...ori,
          ...newFeeds.map((item: FeedItemResponse) => {
            return {
              feedId: item.feedId,
              imageUrl: item.images[0].imageUrl,
            };
          }),
        ];
      });
    }
    // console.log('data has next = ', data.hasNext);
    setHasMore(data.hasNext);
    setCursor(data.nextCursor);
  };

  useEffect(() => {
    loadFeeds();
  }, []);

  return (
    <div className="w-full h-fit bg-light p-12">
      {feedDetail && (
        <MemberFeedModal
          isOpen={isModalOpen}
          setters={{
            setIsOpen: setIsModalOpen,
          }}
          feedDetail={feedDetail}
        />
      )}

      <div className="feed-wrapper flex w-full min-h-fit">
        <InfiniteScroll
          dataLength={feedList.length}
          style={{ display: 'flex' }}
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
