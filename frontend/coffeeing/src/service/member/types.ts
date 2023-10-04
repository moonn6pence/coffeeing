export interface MyInfo {
    memberId: number,
    state: MemberState,
    nickname: string,
    profileImage: string,
    isAfterSurvey: boolean
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
    NORMAL = "NORMAL"
}

export type CoffeeCriteria = {
  roast:number,
  acidity:number,
  body:number
}