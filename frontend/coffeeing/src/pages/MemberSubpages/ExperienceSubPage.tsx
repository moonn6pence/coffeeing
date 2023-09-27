import { MemberId } from 'pages/MemberPage';
import React, { useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { privateRequest } from 'util/axios';
import { API_URL } from 'util/constants';

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

  useEffect(() => {
    // get user experience
    privateRequest
      .get(`${API_URL}/member/experience/${id}`)
      .then(({ data }) => {
        console.log(data);
        setExperienceInfo(data.data);
      });
  }, []);

  return (
    <div className="sub-wrapper">
      {experienceInfo ? (
        <div className="wrapper">
          <div className="header">
            <div className="font-bold text-3xl">
              LV : {experienceInfo.memberLevel}
            </div>
          </div>
          <div className="image-wrapper">
            PLACEHOLDER FOR IMAGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGE
          </div>
          <div className="progress-wrapper">
            <div className="progress-white w-full bg-light h-10 rounded-xl">
              <div
                className={`progress-inner bg-light-roasting h-10 rounded-xl`}
                style={{
                  width:
                    (experienceInfo.experience /
                      experienceInfo.experienceForLevelUp) *
                      100 +
                    '%',
                }}
              ></div>
            </div>
          </div>
        </div>
      ) : (
        <div> halp</div>
      )}
    </div>
  );
};
