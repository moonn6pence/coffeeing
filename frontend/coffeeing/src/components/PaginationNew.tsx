import React, { Dispatch } from "react";
import Next from 'assets/nextarrow.png'
import Prev from 'assets/prevarrow.png'
import DisNext from 'assets/disnextarrow.svg';
import DisPrev from 'assets/disprevarrow.svg'

type Product = {
  id:number,
  nameEng:string,
  brand?:string,
  origin?:string
  imageUrl?:string
}

type PaginationProps = {
  currentPage:number,
  isLast:boolean,
  totalPage:number,
  products:Product[],
  setCurrentPage:Dispatch<React.SetStateAction<number>>
}

export const PaginationNew = ({currentPage, isLast, totalPage,products,setCurrentPage}:PaginationProps)=>{
  const commonClass = 'w-10 h-10 font-semibold text-base';
  return(
    <div>
      {/* 페이지네이션 버튼들 */}
      <div className="flex justify-center">
        <button
          onClick={() => setCurrentPage(currentPage-1)}
          disabled={currentPage === 1}
          className="w-10 h-10"
        >
          <img src={currentPage === 1 ? DisPrev : Prev} />
        </button>
        {/* {pageNums.map((item, index) => (
          <button
            key={index + 1}
            onClick={() => setCurrentPage(index + 1)}
            className={
              index + 1 === currentPage
                ? `${commonClass} text-light-roasting`
                : `${commonClass}`
            }
          >
            {index + 1}
          </button>
        ))} */}
        <button
          onClick={() => setCurrentPage(currentPage+1)}
          disabled={currentPage === totalPage}
          className="w-10 h-10"
        >
          <img src={currentPage === totalPage ? DisNext : Next} />
        </button>
      </div>
    </div>
  )
}