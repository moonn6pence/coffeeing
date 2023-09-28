import React, { useState } from "react";
import DefaultProfile from 'assets/feed/default-profile.svg'
import WriteIcon from 'assets/feed/write-icon.svg';
import DeleteIcon from 'assets/feed/delete-icon.svg';
import FeedUnlike from 'assets/feed/feed-unlike-icon.svg'
import Feedlike from 'assets/feed/feed-like-icon.svg'
import { FeedDetail } from "service/feed/types";
import { NavLink } from 'react-router-dom';
import { TagType } from "service/search/types";
import { useSelector } from 'react-redux';
import { RootState } from 'store/store';
import { Toast } from 'components/Toast';

interface FeedCardProps {
    feedDetail: FeedDetail,
    deleteEventHandler: (feedId: number)=>void,
    likeToggleEventHandler: (feedId: number)=>Promise<boolean|null>,
    editHandler: (feedDetail: FeedDetail)=>void
}

function FeedCard ({ feedDetail, deleteEventHandler, likeToggleEventHandler, editHandler }: FeedCardProps) {
  const { isLogin } = useSelector((state: RootState) => state.member);
  const [liked, setLiked] = useState<boolean>(feedDetail.isLike);
  const [likeCnt, setLikeCnt] = useState<number>(feedDetail.likeCount);
  
  const editEventHandler = () => {
    editHandler(feedDetail);
  }

  const toggleLike = async () => {
    if(!isLogin) {
        Toast.fire('로그인 후 이용해주세요.','','info');
    }

    const res = await likeToggleEventHandler(feedDetail.feedId);
    if(res!==null) {
        setLiked(res);
        setLikeCnt((prev)=>{
            return res ? prev+1 : prev-1;
        })
    }
  }

  return(
    <>
    <div className="feed-card flex flex-col w-full border-b-2 border-light-roasting">
        <div className="feed-header flex flew-row w-full px-22px py-3 justify-between">
            <div className="flex flex-row">
                <div className="feed-avater flex mr-10 justify-center items-center">
                    <NavLink
                        to={`/member/${feedDetail.registerId}`}>
                    {
                        feedDetail.registerProfileImg ? 
                        <img src={feedDetail.registerProfileImg} className="w-12 h-12 rounded-full border-2"/> : 
                        <img src={DefaultProfile} />
                    }
                    </NavLink>
                </div>
                <div className="feed-member-info flex flex-col">
                    <div className="font-bold text-base">
                            <NavLink
                                to={`/member/${feedDetail.registerId}`}>
                                { feedDetail.registerName }
                            </NavLink>
                    </div>
                    {
                        feedDetail.tag ? 
                        <div className="text-sm font-semibold text-white bg-light-roasting rounded px-3 py-1 scale-50 -translate-x-1/4"> 
                            <NavLink
                                to={`/detail/${feedDetail.tag.category === TagType.CAPSULE ? "capsule" : "coffee"}/${feedDetail.tag.tagId}`}>
                                {feedDetail.tag.name} 
                            </NavLink>
                        </div> : ""
                    }
                </div>
            </div>
            {
                feedDetail.isMine ?
                <div className="feed-control-button flex flex-row gap-4">
                    <div className="write-icon-wrapper cursor-pointer rounded-xl" onClick={editEventHandler}>
                        <img src = {WriteIcon} />
                    </div>
                    <div className="delete-icon-wrapper cursor-pointer rounded-xl" onClick={()=>{deleteEventHandler(feedDetail.feedId)}}>
                        <img src = {DeleteIcon} />
                    </div>
                </div>
                : ""
            }
        </div>

        <div className="feed-body flex flex-col w-full py-1">
            <div className="feed-image-wrapper flex w-full">
                <img src = {feedDetail.images[0].imageUrl} className="w-full"/>
            </div>

            <div className="feed-content-wrapper flex flex-col px-6 mt-2">
                <div className="feed-like-wrapper w-full mx-1 flex items-center">
                    <div className="cursor-pointer w-fit rounded-xl"  onClick={toggleLike}>
                        { liked ? <img src = {Feedlike} /> : <img src = {FeedUnlike} /> }
                    </div>
                    <div className="ml-1 text-lg">
                        { likeCnt }
                    </div>
                </div>

                <div className="feed-text-wrapper flex flex-row w-full min-h-max mt-2 mb-5">
                    <p className="break-all"> 
                        { feedDetail.content }
                    </p>
                </div>
            </div>
        </div>
    </div>
    </>
  )
}

export default FeedCard;