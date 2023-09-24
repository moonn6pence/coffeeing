import React, {MouseEvent} from "react";
import DefaultProfile from 'assets/feed/default-profile.svg'
import WriteIcon from 'assets/feed/write-icon.svg';
import DeleteIcon from 'assets/feed/delete-icon.svg';
import SampleImage from 'assets/surveyMainImg.png'
import FeedUnlike from 'assets/feed/feed-unlike-icon.svg'

function FeedCard () {
  return(
    <div className="feed-card flex flex-col w-full aspect-video border-b-2 border-light-roasting">
        <div className="feed-header flex flew-row w-full px-22px py-3 justify-between">
            <div className="flex flex-row">
                <div className="feed-avater flex mr-10 justify-center items-center">
                    <img src={DefaultProfile} />
                </div>
                <div className="feed-member-info flex flex-col">
                    <div>
                        닉네임
                    </div>
                    <div>
                        캡슐이나 원두 태그
                    </div>
                </div>
            </div>
            <div className="feed-control-button flex flex-row gap-1">
                <img src = {WriteIcon} />
                <img src = {DeleteIcon} />
            </div>
        </div>

        <div className="feed-body flex flex-col w-full py-3">
            <div className="feed-image-wrapper w-full">
                <img src = {SampleImage} className="w-full aspect-auto"/>
            </div>

            <div className="feed-content-wrapper px-6">
                <div className="feed-like-wrapper w-full mx-1">
                    <img src = {FeedUnlike} />
                </div>

                <div className="feed-text-wrapper w-full min-h-max">
                    <p> contents ㅁㅇㄴㄹㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㅁㄹㅇㄴㅁㄹㅁㄴㅇㄹㅁㄴ
                    contents ㅁㅇㄴㄹㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㅁㄹㅇㄴㅁㄹㅁㄴㅇㄹㅁㄴ
                    contents ㅁㅇㄴㄹㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㅁㄹㅇㄴㅁㄹㅁㄴㅇㄹㅁㄴ
                    </p>
                </div>
            </div>
        </div>
    </div>
  )
}

export default FeedCard;