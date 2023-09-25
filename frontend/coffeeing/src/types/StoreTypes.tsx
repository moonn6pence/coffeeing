export type SurveyType = {
  currentPage:number;
  totalPage:number;
  roasting:number;
  acidity:number;
  body:number;
  machine:number;
  flavorNote:string,
  isCapsule:boolean; 
}

export type SearchType = {
  searchText:string,
  roasting:number,
  acidity:number,
  body:number,
  aroma:number[],
}
