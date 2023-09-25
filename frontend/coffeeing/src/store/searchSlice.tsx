import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SearchType } from 'types/StoreTypes';

const initialState: SearchType = {
  searchText:'',
  roasting:0,
  acidity:0,
  body:0,
  aroma:[1,2,3]
}

const searchSlice = createSlice({
  name:'searchSlice',
  initialState,
  reducers:{
    setSearchText(state, action:PayloadAction<string>){
      state.searchText = action.payload
      console.log('search text is ', state.searchText)
    }
  },
})

export default searchSlice.reducer;
export const {setSearchText} = searchSlice.actions;