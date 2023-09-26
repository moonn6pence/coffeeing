import React,{ useEffect, useState, useCallback } from "react";
import FeedCard from "components/Feed/FeedCard";
import { getFeeds } from "../service/feed/feed"
import { FeedDetail } from "service/feed/types";
import InfiniteScroll from "react-infinite-scroll-component";
import { FeedEditModal } from "components/Modal/FeedEditModal"
import { useDebounce } from '@react-hooks-hub/use-debounce';
import { Tag } from 'service/search/types';
import { getTagsByKeyword } from 'service/search/search';
import { postFeedLike, deleteFeeds } from "service/feed/feed"

export const FeedPage = () => {

  const [cursor, setCursor] = useState<number|undefined>();
  const [feeds, setFeeds] = useState<Map<number, FeedDetail>>(new Map());
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [createFeedModalOpen, setCreateFeedModalOpen] = useState<boolean>(false);

  const deleteEventHandler = async (feedId: number) => {
    const res = await deleteFeeds(feedId);
    if(res) {
      const newMap = new Map<number, FeedDetail>(feeds);
      newMap.delete(feedId);
      setFeeds(newMap);
    }
  }

  const likeToggleEventHandler = async (feedId: number) => {
    const res = await postFeedLike(feedId);
    return res ? res.result : null;
  }

  const feedComponents = useCallback(()=>{
    return Array.from(feeds.values()).map((feedDetail)=>(
      <FeedCard feedDetail={ feedDetail } 
                key={ feedDetail.feedId }
                suggestions={ suggestions }
                debouncedSearch={ debouncedSearch }
                deleteEventHandler={deleteEventHandler}
                likeToggleEventHandler={likeToggleEventHandler}
                />
    ))
  }, [feeds, setFeeds])
  
  const createFeeds = () => {
      setCreateFeedModalOpen(true);
  }

  const loadFeeds = async () => {
    const result = await getFeeds(cursor, 5);

    if(result) {
      setFeeds((prev)=>{
        const newMap = new Map<number, FeedDetail>(prev);
        prev.forEach(feedDetail=>{
          newMap.set(feedDetail.feedId, feedDetail);
        });
        result.feeds.forEach(feedDetail=>{
          newMap.set(feedDetail.feedId, feedDetail);
        })

        return newMap;
      });
      setHasMore(result.hasNext);
      setCursor(result.nextCursor);
    }
  }

  const [suggestions, setSuggestions] = useState<Tag[]>([]);
  const changeSuggestions = async (keyword: string) => {
    const result = await getTagsByKeyword(keyword);
    if(result) {
      setSuggestions(result.tags);
    }
  };

  const debouncedSearch = useDebounce((keyword: string)=>{
    changeSuggestions(keyword);
  }, 300);

  useEffect(()=>{
    loadFeeds();
  }, []);

  return(
    <>
        <div className="main-container w-320 pt-10 items-center mx-auto mt-32">
            <div className="feed-container flex flex-col w-full h-30 items-center mx-auto min-w-min px-72">
                {/** Create Feed Button */}
                <div className="write-button-wrapper flex flex-row-reverse w-full">
                    <button 
                        className={`px-22px py-3 rounded-3xl hover:brightness-90 cursor-pointer w-fit text-white bg-light-roasting my-2`}
                        onClick={createFeeds}
                        >
                        작성하기
                    </button>
                </div>

                {/** Feed Card Component (Infinite Scroll)*/}
                <div className="feeds-scroll-container flex flex-col w-full min-h-fit gap-1 pb-5">
                    <InfiniteScroll
                        dataLength={feeds.size}
                        next={loadFeeds}
                        hasMore={hasMore}
                        loader={
                            <h4>Loading...</h4>
                        }>
      
                        {  
                          feedComponents()
                        }
                    </InfiniteScroll>
                </div>
            </div>
        </div>

        <FeedEditModal isOpen={ createFeedModalOpen } setIsOpen={ setCreateFeedModalOpen } suggestions = {suggestions} debouncedSearch={debouncedSearch} feedDetail={null} />
    </>
  )
}