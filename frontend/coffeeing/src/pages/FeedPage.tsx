import React,{ useState } from "react";
import Button from "components/Button";
import FeedCard from "components/Feed/FeedCard";

export const FeedPage = () => {
  const createFeeds = () => {
    alert("@")
  }

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
            <div className="feeds-scroll-container w-full min-h-fit gap-1 pb-5">
                <FeedCard />
            </div>
        </div>
    </div>
  )
}