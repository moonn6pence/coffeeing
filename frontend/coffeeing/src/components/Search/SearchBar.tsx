import React, {ChangeEvent, useState} from "react";
import searchIcon from '../../assets/search/search.png'
import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';
import { setSearchText } from "store/searchSlice";

export const SearchBar = ()=>{
  const dispatch = useDispatch<AppDispatch>();
  const [title, setTitle] = useState('')
  const handleTextChange = (e:ChangeEvent<HTMLInputElement>) =>{
    setTitle(e.target.value)
    dispatch(setSearchText(e.target.value))
  }
  return(
      <div className="flex items-center border border-half-light w-790px h-16 rounded-md">
        <span className="flex items-center pl-2">
          <img className="w-5 h-5" src={searchIcon} alt="Search Icon" />
        </span>
        <input 
          className="block py-4 pl-2 w-full focus:outline-none"
          type="text" 
          placeholder="검색어를 입력하세요."
          value={title}
          onChange={(e)=>handleTextChange(e)}
        />
      </div>
  )
}