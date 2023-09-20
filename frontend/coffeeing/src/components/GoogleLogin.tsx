import React from "react";
import googleLogo from '../assets/google_logo.png'

function GoogleLoginBtn(){
  return(
    <a href={`${process.env.REACT_APP_BASE_API_URL}/oauth2/google`}
        className="w-96 h-14 bg-white border border-gray-300 rounded-3xl px-6 py-2 
        flex items-center justify-center gap-2
        hover:bg-gray-200 ">
        <img className="h-7" src={googleLogo}/> 
        <span className="text-sm font-medium text-gray-800">Sign in with Google</span>
    </a>
  )
}

export default GoogleLoginBtn;