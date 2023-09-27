import React,{ useEffect, useState, useCallback } from "react";
import { useNavigate } from 'react-router-dom';
import FeedCard from "components/Feed/FeedCard";
import { getFeeds } from "../service/feed/feed"
import { FeedDetail } from "service/feed/types";
import InfiniteScroll from "react-infinite-scroll-component";
import { FeedEditModal } from "components/Modal/FeedEditModal"
import { useDebounce } from '@react-hooks-hub/use-debounce';
import { Tag } from 'service/search/types';
import { getTagsByKeyword } from 'service/search/search';
import { postFeedLike, deleteFeeds } from "service/feed/feed"
import { Toast } from 'components/Toast';
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import { MemberState } from "service/member/types";

export const FeedPage = () => {
  const navigate = useNavigate();
  const { state } = useSelector((state: RootState) => state.member);
  const [cursor, setCursor] = useState<number|undefined>();
  const [feeds, setFeeds] = useState<Map<number, FeedDetail>>(new Map());
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [createFeedModalOpen, setCreateFeedModalOpen] = useState<boolean>(false);
  const [suggestions, setSuggestions] = useState<Tag[]>([]);
  const [editTarget, setEditTarget] = useState<FeedDetail|null>(null);

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

  const editHandler = (feedDetail: FeedDetail) => {
    setEditTarget(feedDetail);
  };

  const feedComponents = useCallback(()=>{
    return Array.from(feeds.values()).map((feedDetail)=>(
      <FeedCard feedDetail={ feedDetail } 
                key={ feedDetail.feedId }
                deleteEventHandler={deleteEventHandler}
                likeToggleEventHandler={likeToggleEventHandler}
                editHandler={editHandler}
                />
    ))
  }, [feeds, setFeeds])
  
  const createFeeds = () => {
      if(state === MemberState.DEFAULT) {
        Toast.fire('로그인 후 이용해주세요.','','error');
        navigate('/login')
      } else if(state === MemberState.BEFORE_ADDITIONAL_DATA) {
        Toast.fire('추가정보를 입력후 이용해주세요.','','error');
         navigate('/signup/additonal-info')
      } else {
        setCreateFeedModalOpen(true);
      }
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

  useEffect(()=>{
    if(editTarget) {
      setCreateFeedModalOpen(true);
    }
  }, [editTarget])

  return(
    <>
        <div className="main-container w-320 pt-10 items-center mx-auto">
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

        <FeedEditModal isOpen={ createFeedModalOpen } 
                      setIsOpen={setCreateFeedModalOpen} 
                      suggestions = {suggestions} 
                      debouncedSearch={debouncedSearch} 
                      feedDetail={editTarget} 
                      setEditTarget={setEditTarget}/>
    </>
  )
}