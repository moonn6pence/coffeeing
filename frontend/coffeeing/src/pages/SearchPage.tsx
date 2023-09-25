import React, { useEffect, useState } from "react";
import { RoastFilter } from "components/Search/RoastFilter";
import {SearchBar} from "components/Search/SearchBar";
import { ListBox } from "components/Form/ListBoxForm";
import roast from '../assets/search/roast.png'
import acidity from '../assets/search/acitity.png'
// import body from '../assets/search/search.png'

import { ROAST_ITEMS, ACIDITY_ITEMS, BODY_ITEMS, FLAVOR_NOTE_ITEMS } from "util/constants";

export const SearchPage = () =>{
  const [selectedRoast, setSelectedRoast] = useState({value:-1, name:'미선택'})
  const [selectedAcid, setSelectedAcid] = useState({value:-1, name:'미선택'})
  const [selectedBody, setSelectedBody] = useState({value:-1, name:'미선택'})
  const [selectedFlavorNote, setSelectedFlavorNote] = useState({value:-1, name:'미선택'})

  useEffect(()=>{
    console.log(selectedRoast)
  },[selectedRoast,selectedAcid,selectedBody,selectedFlavorNote])

  return(
    <div className="mt-24 flex flex-col items-center gap-3">
      <div className="flex flex-row gap-2">
        <ListBox 
          label="로스팅" 
          selectedItem={selectedRoast} 
          setSelectedItem={setSelectedRoast} 
          itemList={ROAST_ITEMS}
          src={roast}
        />
        <ListBox 
          label="산미" 
          selectedItem={selectedAcid} 
          setSelectedItem={setSelectedAcid} 
          itemList={ACIDITY_ITEMS}
          src={acidity}
        />
        <ListBox 
          label="바디감" 
          selectedItem={selectedBody} 
          setSelectedItem={setSelectedBody} 
          itemList={BODY_ITEMS}
          src={acidity}
        />
        <ListBox 
          label="테이스팅 노트" 
          selectedItem={selectedBody} 
          setSelectedItem={setSelectedBody} 
          itemList={FLAVOR_NOTE_ITEMS}
          src={acidity}
        />
      </div>

      <div className="flex flex-col items-center justify-center">
        <SearchBar/>
      </div>
    </div>
  )
}