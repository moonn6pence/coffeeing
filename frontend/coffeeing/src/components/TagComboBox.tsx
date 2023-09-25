import React, { useState } from 'react'
import { Combobox } from '@headlessui/react'
import { Tag } from 'service/search/types'

interface TagComboBoxProps {
  selectedTag: Tag | undefined,
  changeSelectedTag: any,
  suggestions: Tag[],
  onChange: any,
}

export const TagComboBox = ({selectedTag, changeSelectedTag, suggestions, onChange}: TagComboBoxProps) => {

  return (
    <Combobox value={selectedTag} onChange={changeSelectedTag}>
      <div className='pt-3 px-2'>
        <Combobox.Input
          onChange={(event) => { 
            onChange(event.target.value); 
          }}
          displayValue={()=>selectedTag ? selectedTag.name : ""}
          className={"w-full focus:outline-none border border-gray-200 py-3 px-3 rounded-3xl mb-2"}
          placeholder='캡슐/커피명을 검색하세요...'
        />
      </div>
      
      <div className='h-100px overflow-y-auto'>
        <Combobox.Options className="w-full">
          {suggestions.map((suggestion) => (
            <Combobox.Option
              key={suggestion.tagId}
              value={suggestion}
              className="w-full py-2"
              onClick={(e)=>{
                console.log(e.target);
              }}
            >
              {suggestion.name}
            </Combobox.Option>
          ))}
        </Combobox.Options>
      </div>
    </Combobox>
  )
}