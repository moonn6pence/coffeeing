import React from 'react';
import zeroBean from 'assets/detail/zero_bean.png';
import halfBean from 'assets/detail/half_bean.png';
import oneBean from 'assets/detail/one_bean.png';

export const BeanRating = ({ rate }: { rate: number }) => {
  return (
    <div>
      <img src={zeroBean} alt="빈콩" />
      <img src={halfBean} alt="반콩" />
      <img src={oneBean} alt="찬콩" />
    </div>
  );
};
