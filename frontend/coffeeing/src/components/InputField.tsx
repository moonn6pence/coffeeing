import React, { ChangeEvent } from "react";

type InputFieldProps = {
  label:string;
  placeholder:string;
  value:string;
  type:string;
  onChange:(e:ChangeEvent<HTMLInputElement>)=>void;
}

function InputField({ label, placeholder, value, type, onChange }:InputFieldProps) {
  return (
    <div className="flex flex-col gap-1">
      <label className="text-base text-gray-800 font-medium">{label}</label>
      <input
        className="w-96 h-14 border border-gray-400 pl-4 rounded-xl"
        placeholder={placeholder}
        value={value}
        type={type}
        onChange={onChange}
      />
    </div>
  );
}

export default InputField;
