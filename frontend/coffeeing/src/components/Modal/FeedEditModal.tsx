import React from 'react';
import { Fragment, useRef, useState, useCallback, useEffect } from 'react'
import { Dialog, Transition } from '@headlessui/react'
import UploadImageIcon from "assets/upload-img-icon.svg"
import QuitModalIcon from "assets/quit-modal-icon.svg"
import classNames from 'classnames';

interface FeedEditModalProps {
    isOpen: boolean,
    setIsOpen: any
}

export const FeedEditModal = ({ isOpen, setIsOpen }:FeedEditModalProps) => {

  const cancelButtonRef = useRef(null);
  const [step, setStep] = useState<number>(1);
  const [isDragging, setIsDragging] = useState<boolean>(false);
  const dragRef = useRef<HTMLDivElement | null>(null);

  const handleDragIn = useCallback((e: DragEvent): void => {
    e.preventDefault();
    e.stopPropagation();
  }, []);

  const handleDragOut = useCallback((e: DragEvent): void => {
    e.preventDefault();
    e.stopPropagation();

    setIsDragging(false);
  }, []);

  const handleDragOver = useCallback((e: DragEvent): void => {
    e.preventDefault();
    e.stopPropagation();

    if (e.dataTransfer!.files) {
      setIsDragging(true);
    }
  }, []);

  const handleDrop = useCallback(
    (e: DragEvent): void => {
      e.preventDefault();
      e.stopPropagation();

      setIsDragging(false);
  }, []);

  const initDragEvents = useCallback((): void => {
    if (dragRef.current !== null) {
      console.log(dragRef)
      dragRef.current.addEventListener("dragenter", handleDragIn);
      dragRef.current.addEventListener("dragleave", handleDragOut);
      dragRef.current.addEventListener("dragover", handleDragOver);
      dragRef.current.addEventListener("drop", handleDrop);
    }
  }, [handleDragIn, handleDragOut, handleDragOver, handleDrop]);

  const resetDragEvents = useCallback((): void => {
    if (dragRef.current !== null) {
      dragRef.current.removeEventListener("dragenter", handleDragIn);
      dragRef.current.removeEventListener("dragleave", handleDragOut);
      dragRef.current.removeEventListener("dragover", handleDragOver);
      dragRef.current.removeEventListener("drop", handleDrop);
    }
  }, [handleDragIn, handleDragOut, handleDragOver, handleDrop]);

  useEffect(() => {
    initDragEvents();
    return () => resetDragEvents();
  }, [initDragEvents, resetDragEvents]);

  return (
    <Transition.Root show={isOpen} as={Fragment}>
      <Dialog as="div" className="relative z-10" initialFocus={cancelButtonRef} onClose={setIsOpen}>
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
                              <div className="flex-none cursor-pointer" onClick={()=>setIsOpen(false)}>
                                <img src={QuitModalIcon} />
                              </div>
                            </div>
                       </Dialog.Title>

                      <div className="mt-2 border-b border-gray-200"></div>
                      {/** 이미지 드롭 다운 */}
                      <div className={classNames(
                        isDragging ? "bg-gray-200" : "",
                        "drop-down-uploader mt-2 flex flex-col w-560px items-center aspect-square justify-center")} ref={dragRef}>
                        <img src={UploadImageIcon} />
                        <p className="text-xl text-gray-500 mt-4">
                          사진을 여기에 끌어다 놓으세요
                        </p>
                        
                        <label htmlFor="imageInput">
                          <div className="w-fit justify-center mt-6 px-12 py-3  rounded-md bg-light-roasting text-sm font-semibold text-white shadow-sm hover:bg-cinamon-roasting">
                                컴퓨터에서 선택
                          </div>
                        </label>
                        <input id="imageInput" className="hidden" type="file" accept="image/*"/>
                      </div>
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