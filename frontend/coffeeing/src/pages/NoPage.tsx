import React from 'react';
import noPage from 'assets/nopage.png';

export const NoPage = () => {
  return (
    <div className="flex flex-col items-center mt-10">
      <img src={noPage} alt="없는 페이지" className="w-1/3" />
      <p>존재하지 않는 페이지입니다</p>
    </div>
  );
};
