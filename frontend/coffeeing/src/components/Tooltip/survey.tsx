import React from 'react';
import { Tooltip } from 'react-tooltip';
import IonIcon from '@reacticons/ionicons';

type ToolTipPros = {
  label:string|undefined
}

export const RoastingTooltip = ({label}:ToolTipPros) => {

  return (
    <a className='ml-1' data-tooltip-id='roasting' data-tooltip-place='bottom'  data-tooltip-content={label}>
      <Tooltip id='roasting' />
      <IonIcon name="help-circle-outline"  ></IonIcon>
    </a>
  );
};
