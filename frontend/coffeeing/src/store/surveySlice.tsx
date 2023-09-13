import {createSlice,PayloadAction} from '@reduxjs/toolkit'
import { SurveyType } from 'types/StoreTypes';


const initialState:SurveyType = {
  currentPage:1,
  totalPage:0,
  roasting:0,
  acid:0,
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
    addCurrentPage(state){
      state.currentPage += 1
    }
  },
})

export default surveySlice.reducer;
export const {addCurrentPage} = surveySlice.actions;