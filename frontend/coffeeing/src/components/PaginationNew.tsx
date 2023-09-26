import React, { Dispatch } from "react";
import Next from 'assets/nextarrow.png'
import Prev from 'assets/prevarrow.png'
import DisNext from 'assets/disnextarrow.svg';
import DisPrev from 'assets/disprevarrow.svg'
import { BeanCard } from "./BeanCard";

type Product = {
  id:number,
  nameEng:string,
  nameKr:string,
  brand?:string ,
  origin?:string
  imageUrl:string
}

export type PaginationProps = {
  currentPage:number,
  totalPage:number,
  products:Product[],
  setCurrentPage:Dispatch<React.SetStateAction<number>>
}

export const PaginationNew = ({currentPage,totalPage,products,setCurrentPage}:PaginationProps)=>{
  const commonClass = 'w-10 h-10 font-semibold text-base';
  const maxButtons = 10
  const startPage= Math.max(1, currentPage-Math.floor(maxButtons/2))
  const endPage = Math.min(totalPage, startPage+maxButtons-1)
  return(
    <div>
      {/* 카드들 */}
      <div className="flex flex-wrap w-300 justify-between">
        {products.map((item)=>{
          return(
            <BeanCard 
              id={item.id} 
              subtitle="추후 받아와서 연결해줘야 함" 
              name={item.nameKr}
              isCapsule={true}
              imgLink={item.imageUrl}
              key={item.id} 
            />
          )
        })}
      </div>
      {/* 페이지네이션 버튼들 */}
      <div className="flex justify-center">
        {/* 맨 처음 버튼 */}
        <button
          onClick={()=>setCurrentPage(1)}
          disabled={currentPage==1}
        >처음으로</button>
        {/* 이전 버튼 */}
        <button
          onClick={() => setCurrentPage(currentPage-1)}
          disabled={currentPage === 1}
          className="w-10 h-10"
        >
          <img src={currentPage === 1 ? DisPrev : Prev} />
        </button>
        {/* 페이지 숫자들 */}
        {Array.from({ length: endPage-startPage }, (_, index) => (
          <button
            key={index + 1}
            onClick={() => setCurrentPage(startPage+index)}
            className={startPage+index=== currentPage ? `${commonClass} text-light-roasting` : `${commonClass}`}
          >
            {startPage+index}
          </button>
        ))}
        {/* 다음 버튼 */}
        <button
          onClick={() => setCurrentPage(currentPage+1)}
          disabled={currentPage === totalPage}
          className="w-10 h-10"
        >
          <img src={currentPage === totalPage ? DisNext : Next} />
        </button>
        {/* 맨 뒤로 버튼 */}
        <button
          onClick={()=>setCurrentPage(totalPage)}
          disabled={currentPage===totalPage}
          className="pointer"
        >맨 뒤로</button>
      </div>
    </div>
  )
}