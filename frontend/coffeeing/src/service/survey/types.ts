export interface surveyResult {
  roast:number,
  acidity:number,
  body:number,
  recommendation:recommendation,
  nickname:string,
  imageUrl:string,
}

export interface recommendation {
  isCapsule:boolean,
  products:[{
    id:number,
    imageUrl:string,
    subtitle:string,
    title:string
  }],
}

export interface mySurvey {
  roast:number,
  acidity:number,
  body:number,
  flavorNote:string,
  isCapsule:boolean,
  machineType: number,
}