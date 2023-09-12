import React,{MouseEvent} from "react";
import googleLogo from '../assets/google_logo.png'


function GoogleLoginBtn(){

  // 구글 로그인 처리 function
  const handleGoogleLogin = (e:MouseEvent) =>{
    e.preventDefault();
    console.log('구글 로그인 처리')
  }

  return(
    <button 
        className="w-96 h-14 bg-white border border-gray-300 rounded-3xl shadow-md px-6 py-2 
        flex items-center justify-center gap-2
        hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500 "
        onClick={handleGoogleLogin}
        >
        <img className="h-7" src={googleLogo}/> 
        <span className="text-sm font-medium text-gray-800">Sign in with Google</span>
    </button>
  )
}

export default GoogleLoginBtn;