export interface MyInfo {
    memberId: number,
    state: MemberState,
    nickname: string|undefined,
    profileImage: string|undefined,
}

export enum MemberState {
    BEFORE_ADDITIONAL_DATA = "BEFORE_ADDITIONAL_DATA",
    BEFORE_RESEARCH = "BEFORE_RESEARCH",
    NORMAL = "NORMAL"
}