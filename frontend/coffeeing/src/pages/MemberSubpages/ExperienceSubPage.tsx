import { MemberId } from 'pages/MemberPage';
import React, { useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import IonIcon from '@reacticons/ionicons';
import coffee0 from '../../assets/experience/coffee0.png';
import coffee1 from '../../assets/experience/coffee1.png';
import coffee2 from '../../assets/experience/coffee2.png';
import coffee3 from '../../assets/experience/coffee3.png';

type ExperienceInfo = {
  experience: number;
  experienceForLevelUp: number;
  memberLevel: number;
};

export const ExperienceSubPage = () => {
  const { id } = useOutletContext<MemberId>();
  const [experienceInfo, setExperienceInfo] = useState<
    ExperienceInfo | undefined
  >(undefined);
  const [infoTabOpen, setInfoTabOpen] = useState(false);

  useEffect(() => {
    // get user experience
    privateRequest
      .get(`${API_URL}/member/experience/${id}`)
      .then(({ data }) => {
        console.log(data);
        setExperienceInfo(data.data);
      });
  }, []);

  const handleInfoToggle = (toggle: boolean) => {
    setInfoTabOpen(toggle);
  };

  const setCoffeeImage = (memberLevel: number) => {
    switch (memberLevel) {
      case 0:
        return <img src={coffee0} />;
      case 1:
        return <img src={coffee1} />;
      case 2:
        return <img src={coffee2} />;
      case 3:
        return <img src={coffee3} />;
    }
  };

  return (
    <div className="sub-wrapper">
      {experienceInfo ? (
        <div className="wrapper">
          <div className="header flex items-center justify-between relative">
            <span className="font-bold text-3xl">
              LV : {experienceInfo.memberLevel}
            </span>
            <IonIcon
              name="alert-circle-outline"
              className="font-bold text-2xl cursor-pointer"
              onMouseEnter={() => handleInfoToggle(true)}
              onMouseLeave={() => handleInfoToggle(false)}
            />
            {infoTabOpen ? (
              <div className="absolute right-0 top-10 z-20 border-2 p-5 bg-light rounded">
                <ul>
                  <li>피드 글 작성 + 75</li>
                  <li>리뷰 작성 + 50</li>
                  <li>추천 받기 + 15</li>
                </ul>
              </div>
            ) : (
              ''
            )}
          </div>
          <div className="image-wrapper flex items-center justify-center">
            {setCoffeeImage(experienceInfo.memberLevel)}
          </div>
          <div className="progress-wrapper">
            <div className="progress-white w-full bg-light h-10 rounded-xl relative flex items-center justify-end">
              <div
                className={`progress-inner bg-light-roasting h-10 rounded-xl absolute top-0 left-0`}
                style={{
                  width:
                    (experienceInfo.experience /
                      experienceInfo.experienceForLevelUp) *
                      100 +
                    '%',
                }}
              ></div>
              <span className="font-bold z-10 mr-5">
                {experienceInfo.experience} /{' '}
                {experienceInfo.experienceForLevelUp}
              </span>
            </div>
          </div>
        </div>
      ) : (
        <div> halp</div>
      )}
    </div>
  );
};
