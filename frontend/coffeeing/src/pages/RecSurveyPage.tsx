import React from 'react';
import { useSelector } from 'react-redux';
import { CoffeeSelect } from 'components/Survey/CoffeeSelect';
import { RoastingSelect } from 'components/Survey/RoastingSelect';
import { AciditySelect } from 'components/Survey/AciditySelect';
import { RootState } from 'store/store';

export const RecSurveyPage = () =>{
  const survey = useSelector((state:RootState)=>state.survey)

  return(
    <div>
      {survey.currentPage==0&&
        <CoffeeSelect/>
      }
      {survey.currentPage==1&&
        <RoastingSelect/>
      }
      {survey.currentPage==2&&
        <AciditySelect/>
      }
    </div>
  )
}
