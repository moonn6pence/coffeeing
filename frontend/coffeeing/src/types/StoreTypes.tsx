export type SurveyType = {
  currentPage:number;
  totalPage:number;
  roasting:number;
  acidity:number;
  body:number;
  userId:number;
  machine:number;
  // 원두 or 캡슐 type
  type:number; 
}

export type SearchType = {
  searchText:string,
  roasting:number,
  acidity:number,
  body:number,
  aroma:number[],
}
