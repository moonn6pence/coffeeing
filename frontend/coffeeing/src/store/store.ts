import { configureStore } from "@reduxjs/toolkit";
import surveySlice from "./surveySlice";

export const store = configureStore({
  reducer:{
    survey:surveySlice,
  }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store;