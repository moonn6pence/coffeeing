import { API_URL } from "util/constants"
import { publicRequest } from "util/axios"
import { FilterProps } from "./types"

export const requestFilterResult = ({selectedRoast, selectedAcid,selectedBody,selectedFlavorNote, keyword,productType, page}:FilterProps) => {
  const sendRoast = selectedRoast.slice(1).map(item => item.label).join(', ');
  const sendAcidity = selectedAcid.slice(1).map(item => item.label).join(', ');
  const sendBody = selectedBody.slice(1).map(item => item.label).join(', ');
  const sendFlavorNote = selectedFlavorNote.slice(1).map(item => item.label).join(',');

  const params = {roast:sendRoast, acidity:sendAcidity, body:sendBody, flavorNote:sendFlavorNote,keyword:keyword,productType:productType,page:page,size:8}
  console.log(params)
  publicRequest
  .get(`${API_URL}/search/products`,{params})
  .then((res)=>{
    return res.data
  })
  .catch((err)=>{
    console.log("[filter request fail]")
    return err
  })
}