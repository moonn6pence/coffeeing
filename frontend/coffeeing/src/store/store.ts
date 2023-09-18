import { configureStore } from "@reduxjs/toolkit";
import surveySlice from "./surveySlice";
import searchSlice from "./searchSlice";

export const store = configureStore({
  reducer:{
    survey:surveySlice,
    search:searchSlice,
  }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store;