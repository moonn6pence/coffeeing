import React, { useState } from "react";
import classNames from 'classnames';
import UploadImageIcon from "assets/upload-img-icon.svg"

interface DragDropUploaderProps {
    setImage: any
}

export const DragDropUploader = ( {setImage}: DragDropUploaderProps ) => {

    const [isActive, setActive] = useState<boolean>(false);

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
          setImage(image);
        }
        setActive(false);
      };
      
    
      const handleUpload = async (event: React.ChangeEvent<HTMLInputElement>) => {
        if(event.target.files) {
          setImage(event.target.files[0]);
        }
      };

  return(
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
      </div>
  )
}