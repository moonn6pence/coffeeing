import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SignInMemberInfo } from '../service/auth/types';
import { MemberState, MyInfo } from 'service/member/types';

type LoginState = SignInMemberInfo &
  MyInfo & {
    isLogin: boolean;
  };

const initialState: LoginState = {
  isAfterSurvey: false,
  isLogin: false,
  accessToken: '',
  refreshToken: localStorage.getItem('refreshToken') || '',
  grantType: '',
  memberId: -1,
  nickname: '',
  profileImage: '',
  state: MemberState.DEFAULT,
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
        state: MemberState.DEFAULT
      };
    },
    setMyInfo(state, action: PayloadAction<MyInfo>) {
      const { payload } = action;
      
      console.log(payload);
      return {
        ...state,
        ...payload,
      };
    },
    setMyProfileImage(state, action: PayloadAction<string>) {
      const { payload: newImageUrl } = action;
      return {
        ...state,
        profileImage: newImageUrl,
      };
    },
  },
});

export default memberSlice.reducer;
export const { setMemberToken, logout, setMyInfo, setMyProfileImage } =
  memberSlice.actions;
