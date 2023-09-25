import React, { ElementType, useState } from "react";
import { Dialog, Transition, Disclosure } from '@headlessui/react'
import { useDebounce } from '@react-hooks-hub/use-debounce';
import { Tag, TagType } from 'service/search/types';
import { getTagsByKeyword } from 'service/search/search';
import OpenAccordianIcon from "assets/accordian/open.svg"
import CloseAccordianIcon from "assets/accordian/close.svg"
import DefaultProfile from 'assets/feed/default-profile.svg'
import { TagComboBox } from "../TagComboBox"

interface FeedEditorProps {
    fragment: ElementType<any>,
    preview: string
}

export const FeedEditor = ({ fragment, preview } : FeedEditorProps) => {
    const [openAccordian, setOpenAccordian] = useState<boolean>(false);
    const [selcetedTag, setSelectedTag] = useState<Tag>({
      tagId: -1,
      name: "",
      category: TagType.BEAN
    });
    const [suggestions, setSuggestions] = useState<Tag[]>([]);
    const changeSuggestions = async (keyword: string) => {
      const result = await getTagsByKeyword(keyword);
      if(result) {
        setSuggestions(result.tags);
      }
    };
  
    const toggleAccordianIcon = () => {
      setOpenAccordian((prev)=>{
        return !prev;
      });
    };
  
    const debouncedSearch = useDebounce((keyword: string)=>{
      changeSuggestions(keyword);
    }, 300);

  return(
    <>
        <Transition.Child
            as={ fragment }
            enter="ease-out duration-300"
            enterFrom="opacity-0 translate-y-0"
            enterTo="opacity-100 translate-y-0"
            leave="ease-in duration-200"
            leaveFrom="opacity-100 translate-y-0"
            leaveTo="opacity-0 translate-y-4">

            <div className="w-1056px h-fit">
            <div className='flex'>
                <div className='w-2/3 h-560px'>
                <img src={preview} className='h-full aspect-video object-cover'/>
                </div>

                {/* insert  */}
                <div className='w-1/3 flex flex-col'>
                <div className='current-member-info flex-none flex flex-row w-full px-5 py-3'>
                    <div className='mr-4'>
                    <img src={DefaultProfile} />
                    </div>
                    <div className='flex justify-center items-center font-semibold'>
                        닉네임
                    </div>
                </div>

                <div className='w-full grow'>
                    <textarea rows={8} className='w-full border-y border-gray-200 resize-none focus:outline-none' placeholder='문구를 입력하세요...'>
                    </textarea>
                    <div className='w-full'>
                    <Disclosure>
                        <div className='flex w-full' onClick={toggleAccordianIcon}>
                        <div className='w-full px-2 border-b border-gray-200 pb-1'>
                            <Disclosure.Button className="flex w-full justify-between items-center">
                                <div className='font-semibold'>
                                [원두/캡슐] 태그 검색
                                </div>
                                {openAccordian ? <img src={ OpenAccordianIcon }/> :<img src={ CloseAccordianIcon } />}
                            </Disclosure.Button>
                        </div>
                        </div>
                        <Disclosure.Panel className="text-gray-500 pt-2">
                        <TagComboBox suggestions={suggestions} onChange={debouncedSearch} selectedTag={ selcetedTag } changeSelectedTag={setSelectedTag}/>
                        </Disclosure.Panel>
                    </Disclosure>
                    </div>
                </div>

                <div className='w-full flex flex-row-reverse flex-none mt-4 pr-4'>
                    <button className="w-fit px-12 py-3 rounded-md bg-light-roasting text-sm font-semibold text-white shadow-sm hover:bg-cinamon-roasting">
                    등록하기
                    </button>
                </div>
                </div>
            </div>
        </div>
    </Transition.Child>
  </>
  )
}