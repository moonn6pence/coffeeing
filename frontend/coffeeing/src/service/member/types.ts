export interface MyInfo {
    memberId: number,
    state: MemberState,
    nickname: string,
    profileImage: string,
}

export interface UniqueNickname {
    exist: boolean
}

export interface OnboardRequest {
    nickname: string,
    ageIdx: number,
    genderIdx: number
}

export interface OnboardResult{
    memberId: number,
    nickname: string
}

export enum MemberState {
    DEFAULT = "DEFAULT",
    BEFORE_ADDITIONAL_DATA = "BEFORE_ADDITIONAL_DATA",
    BEFORE_RESEARCH = "BEFORE_RESEARCH",
    NORMAL = "NORMAL"
}