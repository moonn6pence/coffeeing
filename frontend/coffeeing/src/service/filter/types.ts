import { FilterItem } from "util/constants"

export type FilterProps = {
  selectedRoast: FilterItem[],
  selectedAcid:FilterItem[],
  selectedBody:FilterItem[],
  selectedFlavorNote:FilterItem[],
  keyword:string,
  productType:'BEAN'|'CAPSULE',
  page:number,
  size:number,
}

export type FilterSend = {
  roast?: string,
  acidity?:string,
  body?:string,
  flavorNote?:string,
  keyword?:string,
  productType:'BEAN'|'CAPSULE',
  page:number,
  size:number,
}