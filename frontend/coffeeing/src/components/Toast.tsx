import React from "react";
import Swal from "sweetalert2";
import { async } from 'q';

export const Toast = Swal.mixin({
  toast:true,
  position:'top',
  showConfirmButton:false,
  timer:2000,
  width:'350px',
  didOpen(toast) {
    toast.addEventListener("mouseenter", Swal.stopTimer);
    toast.addEventListener("mouseleave", Swal.resumeTimer);
  },

})