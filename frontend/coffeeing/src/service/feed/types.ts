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