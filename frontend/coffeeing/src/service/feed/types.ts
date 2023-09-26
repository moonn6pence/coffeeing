import { Tag } from "../search/types"

interface ImageElement {
    imageUrl: string
}

export interface FeedDetail {
    feedId: number,
    images: ImageElement[],
    content: string,
    tag: Tag | null,
    registerId: number,
    likeCount: number,
    registerName: string,
    registerProfileImg: string | null,
    isLike: boolean,
    isMine: boolean
}

export interface PostFeedReq {
    content: string,
    images: ImageElement[],
    tag: Tag | undefined
}

export interface PostFeedRes {
    feedId: number
}

export interface GetFeedRes {
    feeds: FeedDetail[],
    hasNext: boolean,
    nextCursor: number | undefined
}

export interface PatchFeedReq {
    content: string,
    tag: Tag | undefined
}