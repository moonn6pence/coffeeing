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