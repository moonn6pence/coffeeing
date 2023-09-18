import React from "react";
import { RoastFilter } from "components/Search/RoastFilter";
import {SearchBar} from "components/Search/SearchBar";

export const SearchPage = () =>{
  return(
    <div className="mt-24">
      <RoastFilter/>
      <div className=" flex flex-col items-center justify-center">
        <SearchBar/>
      </div>
    </div>
  )
}