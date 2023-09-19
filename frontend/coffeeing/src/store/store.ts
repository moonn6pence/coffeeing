import { combineReducers, configureStore } from "@reduxjs/toolkit";
import storage from 'redux-persist/lib/storage';
import { persistReducer, persistStore } from 'redux-persist';
import surveySlice from "./surveySlice";
import searchSlice from "./searchSlice";
import memberSlice from "./memberSlice";

const persistConfig = {
  key: 'root',
  storage: storage,
  whitelist: ["member"]
};

const reducers = combineReducers({
  survey:surveySlice,
  search:searchSlice,
  member:memberSlice,
});

const reducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer
})

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store;