import {createSlice,PayloadAction} from '@reduxjs/toolkit'
import { SurveyType } from 'types/StoreTypes';


const initialState:SurveyType = {
  currentPage:0,
  totalPage:0,
  roasting:0,
  acidity:0,
  flavorNote:'',
  body:0,
  machine:1,
  isCapsule:false,
}

const surveySlice = createSlice({
  name:'surveySlice',
  initialState,
  reducers:{
    setTotalPage(state, action:PayloadAction<number>){
      state.totalPage=action.payload
      // console.log('total page', state.totalPage)
    },
    addCurrentPage(state){
      state.currentPage += 1
      // console.log('current page', state.currentPage)
    },
    minusCurrentPage(state){
      state.currentPage -= 1
    },
    saveIsCapsule(state){
      state.isCapsule = true;
    },
    saveRoasting(state, action:PayloadAction<number>){
      state.roasting = action.payload
      // console.log('roasting',state.roasting)
    },
    saveAcidity(state, action:PayloadAction<number>){
      state.acidity = action.payload
      // console.log('acidity', state.acidity)
    },
    saveBody(state, action:PayloadAction<number>){
      state.body=action.payload
      console.log('body', state.body)
    },
    saveFlavorNote(state, action:PayloadAction<string>){
      state.flavorNote = action.payload
      // console.log('flavorNote', state.flavorNote)
    },
    saveMachineType(state, action:PayloadAction<number>){
      state.machine = action.payload
      // console.log('machine type', state.machine)
    },
    resetSurvey:(state)=>{
      state.currentPage=0
      state.totalPage=0
      state.roasting=0
      state.acidity=0
      state.body=0
      state.flavorNote=''
      state.machine=1
      state.isCapsule=false
    }
  },
})

export default surveySlice.reducer;
export const {setTotalPage,addCurrentPage,minusCurrentPage,saveIsCapsule,saveRoasting,saveAcidity,saveBody,resetSurvey,saveFlavorNote,saveMachineType} = surveySlice.actions;