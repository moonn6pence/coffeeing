import React, { useEffect } from 'react';
import { Fragment, useRef, useState } from 'react'
import { Dialog, Transition, Disclosure } from '@headlessui/react'
import UploadImageIcon from "assets/upload-img-icon.svg"
import QuitModalIcon from "assets/quit-modal-icon.svg"
import DefaultProfile from 'assets/feed/default-profile.svg'
import classNames from 'classnames';
import { useDebounce } from '@react-hooks-hub/use-debounce';
import OpenAccordianIcon from "assets/accordian/open.svg"
import CloseAccordianIcon from "assets/accordian/close.svg"

interface FeedEditModalProps {
    isOpen: boolean,
    setIsOpen: any
}

export const FeedEditModal = ({ isOpen, setIsOpen }:FeedEditModalProps) => {

  const cancelButtonRef = useRef(null);
  const [step, setStep] = useState<number>(1);
  const [isActive, setActive] = useState<boolean>(false);
  const [openAccordian, setOpenAccordian] = useState<boolean>(false);
  const [uploadImage, setUploadImage] = useState<File>();
  const [preview, setPreview] = useState<any>();

  const toggleAccordianIcon = () => {
    setOpenAccordian((prev)=>{
      return !prev;
    });
  };

  const debouncedSearch = useDebounce(()=>{
    console.log("debound");
  }, 300);


  const handleDragStart = () => {
    setActive(true);
  }
  const handleDragEnd = () => {
    setActive(false);
  }
  const handleDragOver = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
  };
  const handleDrop = async (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault(); 
    const image = event.dataTransfer.files[0];
    if(image) {
      setUploadImage(image);
      const reader = new FileReader();
      reader.readAsDataURL(image);
      reader.onload = () => {
        setPreview(reader.result);
      }
    }
    setActive(false);
  };
  

  const handleUpload = async (event: React.ChangeEvent<HTMLInputElement>) => {
    if(event.target.files) {
      setUploadImage(event.target.files[0]);
      const reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = () => {
        setPreview(reader.result);
      }
    }
  };

  useEffect(()=>{
    if(preview) {
      setStep(2);
    }
  }, [preview]);
  
  return (
    <Transition.Root show={isOpen} as={Fragment}>
      <Dialog as="div" className="relative z-10" initialFocus={cancelButtonRef} onClose={()=>{
        setIsOpen(false);
        setStep(1);
        setPreview(null);
      }}>
        <Transition.Child
          as={Fragment}
          enter="ease-out duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in duration-200"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
         <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
        </Transition.Child>

        <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
          <div className="flex flex-col min-h-full items-end justify-center p-4 text-center items-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 translate-y-0"
              enterTo="opacity-100 translate-y-0"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 translate-y-0"
              leaveTo="opacity-0 translate-y-4"
            >
              <Dialog.Panel className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all">
                <div className="bg-white pb-4">
                  <div className="flex flex-col">
                    <div className="mt-3 text-center">
                      <Dialog.Title as="h3" className="text-base text-center font-semibold leading-6 text-gray-900">
                            <div className="flex flex-row items-center mx-4">
                              <div className="grow">
                                새 피드 만들기
                              </div>
                              <div className="flex-none cursor-pointer" onClick={() => {
                                    setIsOpen(false);
                                    setStep(1);
                                    setPreview(null);
                              }}>
                                <img src={QuitModalIcon} />
                              </div>
                            </div>
                      </Dialog.Title>

                      <div className="mt-2 border-b border-gray-200"></div>
                      { (step === 1) ? 
                        <div className={
                          classNames(
                            isActive ? 'bg-gray-200' : '',
                            "mt-2 flex flex-col w-560px items-center aspect-square justify-center"
                          )}
                          onDragEnter={ handleDragStart }
                          onDragLeave={ handleDragEnd }
                          onDragOver={ handleDragOver }  
                          onDrop={ handleDrop }
                          >
                          <img src={UploadImageIcon} />
                          <p className="text-xl text-gray-500 mt-4">
                            사진을 여기에 끌어다 놓으세요
                          </p>
                          
                          <label htmlFor="imageInput">
                            <div className="w-fit justify-center mt-6 px-12 py-3  rounded-md bg-light-roasting text-sm font-semibold text-white shadow-sm hover:bg-cinamon-roasting">
                                  컴퓨터에서 선택
                            </div>
                          </label>
                          <input id="imageInput" className="hidden" type="file" accept="image/*" onChange={handleUpload}/>
                        </div> :   
                        <Transition.Child
                          as={Fragment}
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
                                  <textarea rows={8} className='w-full border-y border-gray-200 resize-none focus:outline-none' placeholder='문구를 입력하세요...' onChange={debouncedSearch}>
                                  </textarea>
                                  <div className='w-full' onClick={toggleAccordianIcon}>
                                    <Disclosure>
                                      <div className='flex w-full'>
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
                                        {/** tag input area */}
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
                      }
                      </div>
                  </div>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  )
}