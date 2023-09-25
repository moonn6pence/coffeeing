import { combineReducers, configureStore } from "@reduxjs/toolkit";
import storageSession from 'redux-persist/lib/storage/session';
import { persistReducer, persistStore, FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER } from 'redux-persist';
import surveySlice from "./surveySlice";
import searchSlice from "./searchSlice";
import memberSlice from "./memberSlice";

const persistConfig = {
  key: 'root',
  storage: storageSession,
  whitelist: ["member"]
};

const reducers = combineReducers({
  survey:surveySlice,
  search:searchSlice,
  member:memberSlice,
});

const reducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer,
  middleware: (getDefaultMiddleware) =>
  getDefaultMiddleware({
    serializableCheck: {
      ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
    },
  }),
})

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store;