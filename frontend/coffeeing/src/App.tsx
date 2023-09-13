import React from 'react';
import { Routes, Route } from 'react-router-dom';
import SignupPage from 'pages/SignupPage';
import LoginPage from 'pages/LoginPage';
import { NavBarBody } from 'components/NavBar/NavBarBody';
import { RecSurveyPage } from 'pages/RecSurveyPage';

function App() {
  return (
    <div>
      <NavBarBody />

      {/* Routes */}
      <Routes>
        <Route path="/signup" element={<SignupPage />}></Route>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path='/recommend-survey' element={<RecSurveyPage/>}></Route>
      </Routes>
    </div>
  );
}

export default App;
