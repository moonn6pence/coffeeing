import React,{ useState } from "react";
import Button from "components/Button";

export const FeedPage = () => {
  const createFeeds = () => {
    alert("@")
  }

  return(
    <div className="main-container w-320 pt-10 items-center mx-auto mt-32" style={{
        background: "black"
    }}>


        <div className="feed-container flex flex-col w-full h-30 items-center mx-auto min-w-min px-72" style={{
            background: "green"
        }}>
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
            <div className="feeds-scroll-container w-full h-30 gap-1" style={{
                background: "yellow"
            }}>
                <div className="feed-card flex flex-col w-full aspect-video" style={{
                    background: "yellow"
                }}>
                    <div className="feed-header flex flew-row w-full px-22px py-3" style={{
                        background: "red"
                    }}>
                        <div className="feed-avater mx-1" style={{
                            width: "56px",
                            height: "56px"
                        }}>

                        </div>
                        <div className="feed-member-info flex flex-col">
                            <div>
                                닉네임
                            </div>
                            <div>
                                캡슐이나 원두 태그
                            </div>
                        </div>
                        <div className="feed-control-button">

                        </div>
                    </div>

                    <div className="feed-body flex flex-col w-full px-22px py-3">
                        <div className="feed-image-wrapper w-full" style={{
                            background: "yellow",
                            height: "520px"
                        }}>
                        </div>
                        <div className="feed-like-wrapper w-full" style={{
                            background: "green",
                        }}>
                            <div>
                                하트버튼
                            </div>
                        </div>
                        <div className="feed-content-wrapper w-full" style={{
                            background: "black",
                            height: "320px"
                        }}>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
  )
}