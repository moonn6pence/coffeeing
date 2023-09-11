import React from 'react';
import {Routes,Route,useNavigate} from 'react-router-dom'
import SignupPage from 'pages/SignupPage';

function App() {
  return (
    <div>
      <div>커피잉</div>

      {/* Routes */}
      <Routes>
        <Route path='/signup' element={<SignupPage/>}></Route>
      </Routes>
    </div>
  );
}

export default App;