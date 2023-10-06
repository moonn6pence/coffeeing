import React, { ChangeEvent, useEffect, useState, KeyboardEvent } from "react";
import { ListBox } from "components/Form/ListBoxForm";
import roast from '../assets/search/free-icon-coffee-beans-3219300.png'
import acidity from '../assets/search/free-icon-lemon-7761399.png'
import body from '../assets/search/free-icon-coffee-cup-4952673.png'
import flavorNote from '../assets/search/free-icon-tongue-599326.png'
import { SelectedFilterTag } from "components/Search/SelectedTag"; 
import { ROAST_ITEMS, ACIDITY_ITEMS, BODY_ITEMS, FLAVOR_NOTE_ITEMS } from "util/constants";
import searchIcon from '../assets/search/search.png'
import { requestFilterResult } from "service/filter/request";
import { FilterProps } from "service/filter/types";
import { PaginationNew } from "components/PaginationNew";
import { NoResult } from "components/NoResult";

export const SearchPage = () =>{
  const [selectedRoast, setSelectedRoast] = useState([{label:'미선택', name:'미선택'}])
  const [selectedAcid, setSelectedAcid] = useState([{label:'미선택', name:'미선택'}])
  const [selectedBody, setSelectedBody] = useState([{label:'미선택', name:'미선택'}])
  const [selectedFlavorNote, setSelectedFlavorNote] = useState([{label:'미선택', name:'미선택'}])
  const [keyword, setKeyword] = useState('')
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPage, setTotalPage] = useState(0)
  const [productType, setProductType] = useState('BEAN')
  const [products, setProducts] = useState([])
  const [showResult, setShowResult] = useState(true)
  const [sendKeyword, setSendKeyword] = useState(false)

  const handleTextChange = (e:ChangeEvent<HTMLInputElement>) =>{
    setKeyword(e.target.value)
  }
  const handleEnter = (e:KeyboardEvent<HTMLElement>)=>{
    if (e.key==='Enter'){
      setSendKeyword(!sendKeyword)
    }
  }
  // 필터 결과 가져오기 함수
  const getResult = async () => {
    const filterProps: FilterProps = {
      selectedRoast,
      selectedAcid,
      selectedBody,
      selectedFlavorNote,
      keyword,
      productType,
      page: currentPage, 
      size: 8,
    };
    const result = await requestFilterResult(filterProps);
    // console.log('filtered result',result)
    if (result.totalPage===-1) {
      setShowResult(false)
    } else {
      setShowResult(true)
    }
    setProducts(result.products)
    setTotalPage(result.totalPage)
  }

  // 페이지네이션 페이지 바뀔 때 마다 필터링 결과 가져오기
  useEffect(()=>{
    getResult()
  },[currentPage,productType])

  // 필터 조건 바뀔 때 마다 가져오기
  useEffect(() =>  {  
    if (currentPage===0) {
      getResult()
    } else {
      setCurrentPage(0)
    }
  }, [selectedRoast, selectedAcid, selectedBody, selectedFlavorNote,sendKeyword]);
  
  return(
    <div >
      <div className="mt-10 flex flex-col items-center">
        <div className="flex flex-col gap-2 w-full ">
          {/* 검색바 */}
          <div className="flex flex-col justify-center mx-[20%] ">
            <div className="flex items-center border border-light-roasting h-16 rounded-md">
            <span className="flex items-center pl-2">
              <img onClick={()=>setSendKeyword(!sendKeyword)} className="w-5 h-5" src={searchIcon} alt="Search Icon" />
            </span>
            <input 
              className="block py-4 pl-2 w-full focus:outline-none"
              type="text" 
              placeholder="검색어를 입력하세요."
              value={keyword}
              onKeyDown={handleEnter}
              onChange={(e)=>handleTextChange(e)
              }
            />
          </div>
          <hr className=" border-half-light mt-2" />
          </div>
          {/* 필터 listbox */}
          <div className="flex flex-row gap-2 items-start mx-[20%] ">
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
              src={body}
            />
            <ListBox 
              label="테이스팅 노트" 
              selectedItem={selectedFlavorNote} 
              setSelectedItem={setSelectedFlavorNote} 
              itemList={FLAVOR_NOTE_ITEMS}
              src={flavorNote}
            />
          </div>
          
          {/* 선택된 태그들 */}
          <div className="flex flex-wrap mx-[20%] gap-1">
            <SelectedFilterTag src={roast} selectedItem={selectedRoast} setSelectedItem={setSelectedRoast}/>
            <SelectedFilterTag src={acidity} selectedItem={selectedAcid} setSelectedItem={setSelectedAcid}/>
            <SelectedFilterTag src={body} selectedItem={selectedBody} setSelectedItem={setSelectedBody}/>
            <SelectedFilterTag src={flavorNote} selectedItem={selectedFlavorNote} setSelectedItem={setSelectedFlavorNote}/>
          </div>
        </div>
      </div>

      {/* 검색 결과 페이지네이션 */}
      {/* 원두 | 캡슐 */}
      <div className="text-3xl font-bold space-x-10 mt-10 ml-[10%]">
        <span 
          className={`${productType==='BEAN'?`text-black`:`text-[#7A88A3]`} cursor-pointer `}
          onClick={()=>{setProductType('BEAN'), setCurrentPage(0)}}
          >원두</span>
        <span 
          className={`${productType==='CAPSULE'?`text-black`:`text-[#7A88A3]`} cursor-pointer`}
          onClick={()=>{setProductType('CAPSULE'), setCurrentPage(0)}}
          >캡슐</span>
      </div>
      {/* 검색 결고 없을 때 */}
      <div className="mt-2">
      {!showResult&& <NoResult label="검색 결과가 없습니다."/>}
      </div>
      {/* <div className="flex flex-col items-center"> */}
        {productType==='BEAN'&&totalPage!==-1&&(
          <PaginationNew 
            currentPage={currentPage} 
            totalPage={totalPage} 
            products={products} 
            isCapsule={false}
            setCurrentPage={setCurrentPage} 
          />
        )}
        {productType=='CAPSULE'&&totalPage!==-1&&(
          <PaginationNew 
            currentPage={currentPage} 
            totalPage={totalPage} 
            products={products} 
            isCapsule={true}
            setCurrentPage={setCurrentPage} 
          />
        )}
        <p className="mb-20"></p>
    </div>
  )
}