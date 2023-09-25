import React from 'react'
import { Combobox } from '@headlessui/react'
import { Tag, TagType } from 'service/search/types'
import QuitModalIcon from "assets/quit-modal-icon.svg"

interface TagComboBoxProps {
  selectedTag: Tag,
  changeSelectedTag: any,
  suggestions: Tag[],
  onChange: any,
}

export const TagComboBox = ({selectedTag, changeSelectedTag, suggestions, onChange}: TagComboBoxProps) => {
  
  const deleteSelectedTag = () => {
    changeSelectedTag({
      tagId: -1,
      name: "",
      category: TagType.BEAN
    })
  }

  return (
    <>
    {
      (selectedTag.tagId === -1) ? 
        <Combobox value={selectedTag}>
          <div className='pt-3 px-2 transition-opacity'>
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
                  onClick={()=>{
                    changeSelectedTag(suggestion)
                  }}
                >
                  {suggestion.name}
                </Combobox.Option>
              ))}
            </Combobox.Options>
          </div>
        </Combobox> :
        <div className="w-full flex items-center justify-center transition-opacity">
          <div className="w-fit flex bg-gray-200 rounded pl-5 text-black font-bold items-center justify-center">
            <div className="tag">
              {selectedTag.name}
            </div>
            <div className="cursor-pointer" onClick={ deleteSelectedTag }>
              <img src={QuitModalIcon} className="scale-50"/>
            </div>
          </div>
        </div>
    }
    </>
  )
}