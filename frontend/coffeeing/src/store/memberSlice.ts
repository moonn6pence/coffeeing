import { Action, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SignInMemberInfo } from '../service/auth/types';
import { MyInfo } from 'service/member/types';

type LoginState = SignInMemberInfo & {
  isLogin: boolean;
  myInfo: MyInfo | void;
};

const initialState: LoginState = {
  isLogin: false,
  accessToken: '',
  refreshToken: localStorage.getItem('refreshToken') || '',
  grantType: '',
  myInfo: undefined,
};

const memberSlice = createSlice({
  name: 'memberSlice',
  initialState,
  reducers: {
    setMemberToken(state, action: PayloadAction<SignInMemberInfo>) {
      const payload = action.payload;
      localStorage.setItem('refreshToken', payload.refreshToken);

      return {
        ...state,
        isLogin: true,
        accessToken: payload.accessToken,
        refreshToken: payload.refreshToken,
        grantType: payload.grantType,
      };
    },
    logout(state) {
      localStorage.removeItem('refreshToken');
      return {
        ...state,
        isLogin: false,
        accessToken: '',
        refreshToken: '',
        grantType: '',
      };
    },
    setMyInfo(state, action: PayloadAction<MyInfo>) {
      const { payload } = action;
      return {
        ...state,
        myInfo: payload,
      };
    },
  },
});

export default memberSlice.reducer;
export const { setMemberToken, logout,setMyInfo } = memberSlice.actions;
