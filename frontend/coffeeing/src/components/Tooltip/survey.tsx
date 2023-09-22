import React from 'react';
import { Tooltip } from 'react-tooltip';
import IonIcon from '@reacticons/ionicons';

export const RoastingTooltip = () =>{
  return(
    <a data-tooltip-id='roasting' data-tooltip-content='라이트 로스팅:강한 신맛이 나고 품종의 특성이 잘 나타납니다.'>
        <Tooltip id='roasting'/>
        <IonIcon name="help-circle-outline" size='large'></IonIcon>
    </a>
  )
}