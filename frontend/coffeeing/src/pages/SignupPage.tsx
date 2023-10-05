import React, { useEffect, useState, KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import InputField from 'components/InputField';
import Button from 'components/Button';
import GoogleLoginBtn from 'components/GoogleLogin';
import { signUp } from '../service/auth/auth';
import { getMyInfo } from '../service/member/member';
import { useDispatch } from 'react-redux';
import { AppDispatch } from 'store/store';
import { setMemberToken } from 'store/memberSlice';
import { MemberState } from 'service/member/types';
import { Toast } from 'components/Toast';

function SignupPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [isValidEmail, setIsValidEmail] = useState(true);
  const [pw1, setPw1] = useState('');
  const [isValidPw, setIsValidPw] = useState(true);
  const [pw2, setPw2] = useState('');
  const [isIdenticalPw, setIsIdenticalPw] = useState(true);
  const dispatch = useDispatch<AppDispatch>();

  // validation check
  useEffect(() => {
    setIsValidEmail(/\S+@\S+\.\S+/.test(email));
    // console.log(/\S+@\S+\.\S+/.test(email));
  }, [email]);
  useEffect(() => {
    const passwordPattern =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
    setIsValidPw(passwordPattern.test(pw1));
  }, [pw1]);
  useEffect(() => {
    setIsIdenticalPw(pw1 === pw2);
  }, [pw1, pw2]);

  useEffect(() => {
    const checkMemberInfo = async () => {
      const result = await getMyInfo();
      if (result && result.state == MemberState.BEFORE_ADDITIONAL_DATA) {
        window.location.replace('/signup/additonal-info');
      }

      if (result && result.state == MemberState.NORMAL) {
        window.location.replace('/recommend-main');
      }
    };
    checkMemberInfo();
  }, []);

  // 회원가입 처리
  const handleSubmit = async () => {
    if (!email || !pw1 || !pw2 || pw1 !== pw2) return;
    const result = await signUp({ email: email, password: pw1 });
    if (result) {
      dispatch(setMemberToken(result));

      const myInfo = await getMyInfo();
      if (myInfo && myInfo.state == MemberState.BEFORE_ADDITIONAL_DATA) {
        window.location.replace('/signup/additonal-info');
      }
      if (myInfo && myInfo.state == MemberState.NORMAL) {
        window.location.replace('/recommend-main');
      }
    } else {
      Toast.fire('이미 존재하는 이메일입니다.', '', 'error');
    }
  };
  // enter key event 추가
  const handleEnter = (e: KeyboardEvent<HTMLElement>) => {
    if (e.key === 'Enter') {
      handleSubmit();
    }
  };

  return (
    <div className="flex flex-col gap-5 items-center pt-10 mb-20">
      <div className="text-3xl font-bold">회원가입</div>
      <div className="flex flex-row gap-1">
        <p>이미 회원이신가요?</p>
        <p
          className="font-bold cursor-pointer"
          onClick={() => {
            navigate('/login');
          }}
        >
          로그인
        </p>
      </div>
      {/* 이메일 */}
      <div className="flex flex-col gap-1">
        <InputField
          label="이메일"
          placeholder="이메일을 입력해주세요."
          value={email}
          type="email"
          onChange={(e) => setEmail(e.target.value)}
          onKeyDown={handleEnter}
        />
        {!isValidEmail && email && (
          <div className="text-xs text-red-600">
            이메일 형식이 잘못되었습니다.
          </div>
        )}
      </div>
      {/* 비밀번호 */}
      <div className="flex flex-col gap-1">
        <InputField
          label="비밀번호"
          placeholder="비밀번호를 입력해주세요."
          value={pw1}
          type="password"
          onChange={(e) => setPw1(e.target.value)}
          onKeyDown={handleEnter}
        />
        {!isValidPw && pw1 && (
          <div className="text-xs text-red-600">
            비밀번호는 8자 이상으로 영문, 숫자, 특수기호를 포함해야 합니다.
          </div>
        )}
      </div>
      {/* 비밀번호 확인 */}
      <div className="flex flex-col gap-1">
        <InputField
          label="비밀번호 확인"
          placeholder="비밀번호를 다시 입력해주세요."
          value={pw2}
          type="password"
          onChange={(e) => setPw2(e.target.value)}
          onKeyDown={handleEnter}
        />
        {!isIdenticalPw && pw2 && (
          <div className="text-xs text-red-600">
            비밀번호가 일치하지 않습니다.
          </div>
        )}
      </div>
      {/* 회원가입 버튼 */}
      <Button placeholder="회원가입" handleSubmit={handleSubmit} />
      <div>또는</div>
      {/* 구글 로그인 버튼 */}
      <GoogleLoginBtn />
    </div>
  );
}

export default SignupPage;
