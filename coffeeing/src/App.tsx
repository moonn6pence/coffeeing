import React from 'react';
import {Routes,Route} from 'react-router-dom'
import SignupPage from 'pages/SignupPage';
import LoginPage from 'pages/LoginPage';

function App() {
  return (
    <div>
      <div>커피잉</div>

      {/* Routes */}
      <Routes>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
      </Routes>
    </div>
  );
}

export default App;