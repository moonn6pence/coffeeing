import React, { useState, Fragment } from 'react'
import { Listbox } from '@headlessui/react'
import {Item} from '../../util/constants'
import IonIcon from '@reacticons/ionicons'

type ListBoxProps = {
  label:string,
  selectedItem:Item,
  setSelectedItem:any,
  itemList:Item[];
  src:string;
}

export const ListBox = ({label, selectedItem, setSelectedItem, itemList, src}:ListBoxProps) =>{
  return (
    <Listbox
      value={selectedItem}
      onChange={(newSelectedItems) => setSelectedItem(newSelectedItems)}
      multiple
    >

    <div className="relative ">
      {/* 태그 */}
      <Listbox.Label className="w-fit p-4 h-8 border border-cinamon-roasting rounded-3xl flex gap-2 items-center justify-center">
        <img className="w-4 h-5" src={src}/>
        {label}
        <Listbox.Button
          className="cursor-pointer"
          aria-label="Toggle listbox"
          onClick={() => setSelectedItem([])} 
        >
          ⌄
        </Listbox.Button>
      </Listbox.Label>
      {/* 드롭다운 */}
      <Listbox.Options className="w-full absolute mt-2 py-2 bg-white border border-gray-300 rounded-xl">
        {itemList.map((item) => (
          <Listbox.Option key={item.value} value={item}>
            {({ active, selected }) => (
              <li
                className={`${
                  selected
                    ? 'bg-drop-down text-black'
                    : 'text-gray-900'
                } cursor-pointer select-none relative px-4 py-2`}
              >
              <div className="flex items-center">
                  {selected && (
                    <span className="mr-2">
                      <IonIcon name="checkmark-outline" />
                    </span>
                  )}
                  {item.name}
                </div>
              </li>
            )}
          </Listbox.Option>
        ))}
      </Listbox.Options>

    </div>

    </Listbox>
  );
}