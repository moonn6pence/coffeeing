import {createSlice,PayloadAction} from '@reduxjs/toolkit'
import { SurveyType } from 'types/StoreTypes';


const initialState:SurveyType = {
  currentPage:0,
  totalPage:0,
  roasting:0,
  acidity:0,
  flavorNote:[],
  body:0,
  userId:0,
  machine:0,
  // 원두 or 캡슐 type
  type:0,
}

const surveySlice = createSlice({
  name:'surveySlice',
  initialState,
  reducers:{
    setTotalPage(state, action:PayloadAction<number>){
      state.totalPage=action.payload
      console.log('total page', state.totalPage)
    },
    addCurrentPage(state){
      state.currentPage += 1
      console.log('current page', state.currentPage)
    },
    minusCurrentPage(state){
      state.currentPage -= 1
    },
    saveRoasting(state, action:PayloadAction<number>){
      state.roasting = action.payload
      console.log('roasting',state.roasting)
    },
    saveAcidity(state, action:PayloadAction<number>){
      state.acidity = action.payload
      console.log('acidity', state.acidity)
    },
    saveBody(state, action:PayloadAction<number>){
      state.body=action.payload
      console.log('body', state.body)
    },
    saveFlavorNote(state, action:PayloadAction<Array<string>>){
      state.flavorNote = action.payload
      console.log('flavorNote', state.flavorNote)
    },
    resetSurvey:()=>initialState
  },
})

export default surveySlice.reducer;
export const {setTotalPage,addCurrentPage,minusCurrentPage,saveRoasting,saveAcidity,saveBody,resetSurvey,saveFlavorNote} = surveySlice.actions;