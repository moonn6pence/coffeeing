import { Action, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SignInMemberInfo } from "../service/auth/types"

type LoginState = SignInMemberInfo & {isLogin: boolean};

const initialState: LoginState = {
    isLogin: false,
    accessToken:'',
    refreshToken: localStorage.getItem("refreshToken") || '',
    grantType:''
}

const memberSlice = createSlice({
  name:'memberSlice',
  initialState,
  reducers:{
    setMemberToken(state, action:PayloadAction<SignInMemberInfo>) {
        const payload = action.payload;
        localStorage.setItem("refreshToken", payload.refreshToken);

        return {
            ...state,
            isLogin: true,
            accessToken: payload.accessToken,
            refreshToken: payload.refreshToken,
            grantType: payload.grantType
        };
    },
    logout(state) {
      localStorage.removeItem("refreshToken");
      return {
        ...state,
        isLogin: false,
        accessToken: "",
        refreshToken: "",
        grantType: ""
      }
    }
  },
})

export default memberSlice.reducer;
export const { setMemberToken, logout } = memberSlice.actions;