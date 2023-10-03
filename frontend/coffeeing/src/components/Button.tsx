import React, {MouseEvent} from "react";

type Placeholder = {
  placeholder:string;
  handleSubmit:(e:MouseEvent<HTMLButtonElement>)=>void;
}

function Button({placeholder,handleSubmit}:Placeholder) {
  return(
    <div>
      <button 
        className={`w-96 h-14 text-white font-bold rounded-3xl bg-light-roasting`}
        onClick={handleSubmit}
        >
        {placeholder}
      </button>
    </div>
  )
}

export default Button;