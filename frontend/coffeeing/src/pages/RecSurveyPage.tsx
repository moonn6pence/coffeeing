import React from 'react';
import { useSelector } from 'react-redux';
import { CoffeeSelect } from 'components/Survey/CoffeeSelect';
import { RoastingSelect } from 'components/Survey/RoastingSelect';
import { AciditySelect } from 'components/Survey/AciditySelect';
import { BodySelect } from 'components/Survey/BodySelect';
import { FlavorNoteSelect } from 'components/Survey/FlavorNoteSelect';
import { MachineSelect } from 'components/Survey/MachineSelect';
import { RootState } from 'store/store';

export const RecSurveyPage = () =>{
  const survey = useSelector((state:RootState)=>state.survey)

  return(
    <div className="absolute">
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
        {survey.currentPage==3&&
          <BodySelect/>
        } 
        {survey.currentPage==4&&
          <FlavorNoteSelect/>
        }
        {
          survey.currentPage==5&&
          <MachineSelect/>
        }
      </div>
    </div>
  )
}
