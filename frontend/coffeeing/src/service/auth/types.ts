export interface SignUpMemberInfo {
    memberId: number,
    email: string,
    accessToken: string,
    refreshToken: string,
    grantType: string
}

export interface SignInMemberInfo {
    accessToken: string,
    refreshToken: string,
    grantType: string 
}