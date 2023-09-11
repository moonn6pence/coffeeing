import React, {useState} from "react";

function SignupPage(){
  const [email, setEmail] = useState('');
  const [pw1, setPw1] = useState('');
  const [pw2,setPw2] = useState('');

  return(
    <div className="flex flex-col gap-5 items-center">
      <div className="text-4xl font-bold">회원가입</div>
      <div className="flex flex-row">
        <p>이미 회원이신가요?</p> <p >로그인</p>
      </div>
      <div className="flex flex-col gap-1">
        <label className="text-base text-gray-800 font-medium">이메일</label>
        <input 
          className="w-96 h-14 border border-gray-400 pl-4 rounded-xl" 
          type="text" 
          placeholder="이메일을 입력해주세요." 
          />
      </div>
      <div className="flex flex-col gap-1">
        <label className="text-base text-gray-800 font-medium">비밀번호</label>
        <input 
          className="w-96 h-14 border border-gray-400 pl-4 rounded-xl" 
          type="text" 
          placeholder="비밀번호를 입력해주세요." 
          />
      </div>
      <div className="flex flex-col gap-1">
        <label className="text-base text-gray-800 font-medium">비밀번호 확인</label>
        <input 
          className="w-96 h-14 border border-gray-400 pl-4 rounded-xl" 
          type="text" 
          placeholder="비밀번호를 다시 입력해주세요." />
      </div>
    </div>
  )
}

export default SignupPage;