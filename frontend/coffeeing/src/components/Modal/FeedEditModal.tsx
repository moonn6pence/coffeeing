import React, { useEffect } from 'react';
import { Fragment, useRef, useState } from 'react'
import { Dialog, Transition } from '@headlessui/react'

import QuitModalIcon from "assets/quit-modal-icon.svg"
import { DragDropUploader } from 'components/Feed/DragDropUploader';
import { FeedEditor } from 'components/Feed/FeedEditor';

interface FeedEditModalProps {
    isOpen: boolean,
    setIsOpen: any
}

export const FeedEditModal = ({ isOpen, setIsOpen }:FeedEditModalProps) => {

  const cancelButtonRef = useRef(null);
  const [step, setStep] = useState<number>(1);
  const [uploadImage, setUploadImage] = useState<File>();
  const [preview, setPreview] = useState<string>();

  useEffect(()=>{
    if(uploadImage) {
      const reader = new FileReader();
      reader.readAsDataURL(uploadImage);
      reader.onload = () => {
        if(reader.result) {
          setPreview(reader.result as string);
        }
      }
    }
  }, [uploadImage]);

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
        setPreview("");
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
                                    setPreview("");
                              }}>
                                <img src={QuitModalIcon} />
                              </div>
                            </div>
                      </Dialog.Title>

                      <div className="mt-2 border-b border-gray-200"></div>
                      { (step === 1) ? 
                        <DragDropUploader setImage={setUploadImage}/> :   
                        preview ? <FeedEditor fragment={Fragment} preview={preview}/> : ""
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