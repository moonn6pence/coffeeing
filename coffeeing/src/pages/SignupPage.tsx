import React, { useState } from "react";
import InputField from "components/InputField";
import Button from "components/Button";

function SignupPage() {
  const [email, setEmail] = useState("");
  const [pw1, setPw1] = useState("");
  const [pw2, setPw2] = useState("");
  const handleSubmit = (e:React.MouseEvent) =>{
    e.preventDefault();
    console.log('회원가입 처리하기')
  }

  return (
    <div className="flex flex-col gap-5 items-center">
      <div>{email}</div>
      <div className="text-4xl font-bold">회원가입</div>
      <div className="flex flex-row">
        <p>이미 회원이신가요?</p> <p>로그인</p>
      </div>

      <InputField
        label="이메일"
        placeholder="이메일을 입력해주세요."
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <InputField
        label="비밀번호"
        placeholder="비밀번호를 입력해주세요."
        value={pw1}
        onChange={(e) => setPw1(e.target.value)}
      />
      <InputField
        label="비밀번호 확인"
        placeholder="비밀번호를 다시 입력해주세요."
        value={pw2}
        onChange={(e) => setPw2(e.target.value)}
      />
      <Button placeholder="회원가입" handleSubmit={handleSubmit} />
    </div>
  );
}

export default SignupPage;
