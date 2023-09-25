import { TagType } from "service/search/types";
import { generateRandomString } from "util/randomString";
import { FeedDetail } from "./types";

export const getFeedDetailMock = ():FeedDetail => {
    return {
        feedId: Math.floor(Math.random()*100000),
        images: [],
        content: generateRandomString(3200),
        tag: {
            tagId: Math.floor(Math.random()),
            name: generateRandomString(11),
            category: TagType.BEAN
        },
        registerId: Math.floor(Math.random()),
        likeCount: Math.floor(Math.random()),
        registerName: generateRandomString(10),
        registerProfileImg: null,
        isLike: Math.random() >= 0.3,
        isMine: Math.random() >= 0.3
    };
}