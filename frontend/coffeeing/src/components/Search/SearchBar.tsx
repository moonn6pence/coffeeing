import React, {useState} from "react";

export const SearchBar = ()=>{
  const [title, setTitle] = useState('')
  return(
      <input 
        className="border text-xl "
        type="text" 
        value={title} 
        onChange={(e)=>setTitle(e.target.value)}
        placeholder="검색어를 입력해주세요."
        />
  )
}