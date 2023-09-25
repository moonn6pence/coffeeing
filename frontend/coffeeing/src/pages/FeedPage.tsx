import React,{ useEffect, useState } from "react";
import FeedCard from "components/Feed/FeedCard";
import { getFeedDetailMock } from "../service/feed/mock"
import { FeedDetail } from "service/feed/types";
import InfiniteScroll from "react-infinite-scroll-component";
import { FeedEditModal } from "../components/Modal/FeedEditModal"
import { useDebounce } from '@react-hooks-hub/use-debounce';
import { Tag } from 'service/search/types';
import { getTagsByKeyword } from 'service/search/search';

export const FeedPage = () => {
  const [feeds, setFeeds] = useState<FeedDetail[]>([]);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [createFeedModalOpen, setCreateFeedModalOpen] = useState<boolean>(false);

  const createFeeds = () => {
      setCreateFeedModalOpen(true);
  }

  const loadFeeds = () => {
    const mockData = [];
    for(let i=0; i<5; ++i) {
        mockData.push(getFeedDetailMock());
    }

    const newList = [...feeds, ...mockData];
    setFeeds(newList);
    setHasMore(false);
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
    const mockData = [];
    for(let i=0; i<1; ++i) {
        mockData.push(getFeedDetailMock());
    }
    setFeeds(mockData);
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
                        dataLength={feeds.length}
                        next={loadFeeds}
                        hasMore={hasMore}
                        loader={
                            <h4>Loading...</h4>
                        }
                    >
                        {
                            feeds.map((feedDetail)=>(<FeedCard feedDetail={ feedDetail } key={feedDetail.feedId}/>))
                        }
                    </InfiniteScroll>
                </div>
            </div>
        </div>

        <FeedEditModal isOpen={ createFeedModalOpen } setIsOpen={ setCreateFeedModalOpen } suggestions = {suggestions} debouncedSearch={debouncedSearch} />
    </>
  )
}