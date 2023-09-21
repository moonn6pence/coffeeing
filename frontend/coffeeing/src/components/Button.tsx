import React, {MouseEvent} from "react";

type Placeholder = {
  placeholder:string;
  handleSubmit:(e:MouseEvent<HTMLButtonElement>)=>void;
  color?: string;
}

function Button({placeholder,handleSubmit,color="bg-yellow-600"}:Placeholder) {
  return(
    <div>
      <button 
        className={`w-96 h-14 text-white font-bold rounded-3xl ${color}`}
        onClick={handleSubmit}
        >
        {placeholder}
      </button>
    </div>
  )
}

export default Button;