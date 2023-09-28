import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import SignupPage from 'pages/SignupPage';
import LoginPage from 'pages/LoginPage';
import { NavBarBody } from 'components/NavBar/NavBarBody';
import { RecMainPage } from 'pages/RecMainPage';
import { RecSurveyPage } from 'pages/RecSurveyPage';
import { RecResultPage } from 'pages/RecResultPage';
import { ListPage } from 'pages/ListPage';
import { SearchPage } from 'pages/SearchPage';
import { DetailPage } from 'pages/DetailPage';
import { OauthPage } from 'pages/OauthPage';
import { AfterSignupPage } from 'pages/AfterSignupPage';
import { MemberPage } from 'pages/MemberPage';
import { FeedPage } from 'pages/FeedPage';
import { MainPage } from 'pages/MainPage';
import { BookmarkSubPage } from 'pages/MemberSubpages/BookmarkSubPage';
import { FeedSubPage } from 'pages/MemberSubpages/FeedSubPage';
import { ExperienceSubPage } from 'pages/MemberSubpages/ExperienceSubPage';
import { TransitionGroup, CSSTransition } from "react-transition-group"
import "./transition.css"

function App() {
  const location = useLocation();
  return (
    <div>
      <NavBarBody />
      {/* Routes */}
      <TransitionGroup>
        <CSSTransition
          key={location.pathname}
          timeout={300}
          classNames={'fade'}
        >
          <Routes location={location}>
            <Route path="/signup" element={<SignupPage />}></Route>
            <Route path="/login" element={<LoginPage />}></Route>
            <Route path="/recommend-main" element={<RecMainPage />}></Route>
            <Route path="/recommend-survey" element={<RecSurveyPage />}></Route>
            <Route path="/recommend-result" element={<RecResultPage />}></Route>
            <Route path="/beans" element={<ListPage />}></Route>
            <Route path="/search" element={<SearchPage />}></Route>
            <Route path="/detail/:beans/:id" element={<DetailPage />}></Route>
            <Route path="/oauth" element={<OauthPage />}></Route>
            <Route
              path="/signup/additonal-info"
              element={<AfterSignupPage />}
            ></Route>
            <Route path="/member/:id" element={<MemberPage />}>
              <Route
                index
                element={<ExperienceSubPage />}
              ></Route>
              <Route path="bookmark" element={<BookmarkSubPage />}></Route>
              <Route path="feed" element={<FeedSubPage />}></Route>
            </Route>
            <Route path="/feeds" element={<FeedPage />}></Route>
            <Route path="/" element={<MainPage />}></Route>
          </Routes>
        </CSSTransition>
      </TransitionGroup>
    </div>
  );
}

export default App;
