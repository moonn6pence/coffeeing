import React from "react";
import { Item } from "util/constants";

type SelectedTagProps = {
  src: string;
  selectedItem: Item[];
};

export const SelectedFilterTag = ({ src, selectedItem }: SelectedTagProps) => {
  return (
    <div className="flex flex-row gap-1">
      {selectedItem
        .slice(1,)
        .map((item) => (
        <div key={item.value} className="w-fit p-4 h-8 border border-cinamon-roasting rounded-3xl flex gap-2 items-center justify-center">
          <img src={src} alt={item.name} className="w-4 h-5" />
          {item.name}
        </div>
      ))}
    </div>
  );
};
