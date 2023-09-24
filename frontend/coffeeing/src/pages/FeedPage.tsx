import React,{ useEffect, useState } from "react";
import Button from "components/Button";
import FeedCard from "components/Feed/FeedCard";
import { getFeedDetailMock } from "../service/feed/mock"
import { FeedDetail } from "service/feed/types";

export const FeedPage = () => {
  const [feeds, setFeeds] = useState<FeedDetail[]>([]);

  const createFeeds = () => {
      alert("TODO CONNECT FEED API")
  }

  useEffect(()=>{
    const mockData = [];
    for(let i=0; i<5; ++i) {
        mockData.push(getFeedDetailMock());
    }
    setFeeds(mockData);
  }, []);

  return(
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
                {
                    feeds.map((feedDetail)=>(<FeedCard feedDetail={ feedDetail } key={feedDetail.feedId}/>))
                }
            </div>
        </div>
    </div>
  )
}