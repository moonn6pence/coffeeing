import React, { useState } from "react";
import DefaultProfile from 'assets/feed/default-profile.svg'
import WriteIcon from 'assets/feed/write-icon.svg';
import DeleteIcon from 'assets/feed/delete-icon.svg';
import SampleImage from 'assets/surveyMainImg.png'
import FeedUnlike from 'assets/feed/feed-unlike-icon.svg'
import Feedlike from 'assets/feed/feed-like-icon.svg'
import { FeedDetail } from "service/feed/types";
import { postFeedLike } from "service/feed/feed"

interface FeedCardProps {
    feedDetail: FeedDetail,
}

function FeedCard ({ feedDetail }: FeedCardProps) {

  const [liked, setLiked] = useState<boolean>(feedDetail.isLike);
  const editEventHandler = () => {
      alert("edit");
  }

  const deleteEventHandler = () => {
      alert("delete");
  }

  const likeToggleEventHandler = async () => {
    const res = await postFeedLike(feedDetail.feedId);
    if(res) {
        setLiked(res.result);
    }
  }


  return(
    <div className="feed-card flex flex-col w-full border-b-2 border-light-roasting">
        <div className="feed-header flex flew-row w-full px-22px py-3 justify-between">
            <div className="flex flex-row">
                <div className="feed-avater flex mr-10 justify-center items-center">
                    {
                        feedDetail.registerProfileImg ? 
                        <img src={feedDetail.registerProfileImg} className="w-10 h-10 rounded-full border-2"/> : 
                        <img src={DefaultProfile} />
                    }
                </div>
                <div className="feed-member-info flex flex-col">
                    <div>
                        { feedDetail.registerName }
                    </div>
                    {
                        feedDetail.tag ? <div> 캡슐이나 원두 태그</div> : ""
                    }
                </div>
            </div>
            {
                feedDetail.isMine ?
                <div className="feed-control-button flex flex-row gap-4">
                    <div className="write-icon-wrapper cursor-pointer rounded-xl" onClick={editEventHandler}>
                        <img src = {WriteIcon} />
                    </div>
                    <div className="delete-icon-wrapper cursor-pointer rounded-xl" onClick={deleteEventHandler}>
                        <img src = {DeleteIcon} />
                    </div>
                </div>
                : ""
            }
        </div>

        <div className="feed-body flex flex-col w-full py-3">
            <div className="feed-image-wrapper flex w-full">
                <img src = {SampleImage} className="w-full"/>
            </div>

            <div className="feed-content-wrapper flex flex-col px-6">
                <div className="feed-like-wrapper w-full mx-1">
                    <div className="cursor-pointer w-fit rounded-xl"  onClick={likeToggleEventHandler}>
                        { liked ? <img src = {Feedlike} /> : <img src = {FeedUnlike} /> }
                    </div>
                </div>

                <div className="feed-text-wrapper flex flex-row w-full min-h-max">
                    <p className="break-all"> 
                        { feedDetail.content }
                    </p>
                </div>
            </div>
        </div>
    </div>
  )
}

export default FeedCard;