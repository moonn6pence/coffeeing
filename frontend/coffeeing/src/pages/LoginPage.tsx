import React, { useState, useEffect, MouseEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import InputField from 'components/InputField';
import Button from 'components/Button';
import GoogleLoginBtn from 'components/GoogleLogin';

import { signIn } from '../service/auth/auth';
import { getMyInfo } from '../service/member/member';
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { setMemberToken, setMyInfo } from 'store/memberSlice';
import { MemberState } from 'service/member/types';
import { Toast } from 'components/Toast';

function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  const dispatch = useDispatch<AppDispatch>();
  // 로그인 처리
  const handleSubmit = async (e: MouseEvent) => {
    e.preventDefault();
    const result = await signIn({ email: email, password: pw });
    if (result) {
      dispatch(setMemberToken(result));
      const myInfo = await getMyInfo();
      if (myInfo) {
        console.log('login myinfo = ', myInfo);
        dispatch(setMyInfo(myInfo));
      }

      if (myInfo && myInfo.state == MemberState.BEFORE_ADDITIONAL_DATA) {
        window.location.replace('/signup/additonal-info');
      }

      if (myInfo && myInfo.state == MemberState.BEFORE_RESEARCH) {
        window.location.replace('/recommend-main');
      }
    } else {
      Toast.fire('아이디 또는 비밀번호를 <br> 확인하세요.','','error');
    }
  };

  useEffect(() => {
    const checkMemberInfo = async () => {
      const result = await getMyInfo();
      if (result && result.state == MemberState.BEFORE_ADDITIONAL_DATA) {
        window.location.replace('/signup/additonal-info');
      }

      if (result && result.state == MemberState.BEFORE_RESEARCH) {
        window.location.replace('/recommend-main');
      }
    };
    checkMemberInfo();
  }, []);

  return (
    <div className="flex flex-col gap-6 items-center pt-10">
      <div className="text-3xl font-bold">로그인</div>
      <div className="flex flex-row gap-1">
        <p>아직 회원이 아니신가요?</p>
        <p
          className="hover:font-bold cursor-pointer"
          onClick={() => {
            navigate('/signup');
          }}
        >
          회원가입
        </p>
      </div>

      <InputField
        label="이메일"
        placeholder="이메일을 입력해주세요."
        value={email}
        type="email"
        onChange={(e) => setEmail(e.target.value)}
      />
      <InputField
        label="비밀번호"
        placeholder="비밀번호를 입력해주세요."
        value={pw}
        type="password"
        onChange={(e) => setPw(e.target.value)}
      />
      <div className="flex flex-col items-center gap-2">
        <Button placeholder="로그인" handleSubmit={handleSubmit} />
        <p className="text-sm flex flex-row gap-2">
          <span>비밀번호를 잊어버리셨나요?</span>
          <span className="font-semibold">비밀번호 찾기</span>
        </p>
      </div>

      <div className="text-xl">OR</div>
      <GoogleLoginBtn />
    </div>
  );
}

export default LoginPage;
