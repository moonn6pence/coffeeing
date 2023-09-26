import { async } from 'q';
import Swal from 'sweetalert2';

export const DeleteAlert = async () => {
  const result = await Swal.fire({
    text: '정말 삭제하시겠어요?',
    width: '345px',
    denyButtonText: '취소',
    confirmButtonText: '삭제',
    showDenyButton: true,
    reverseButtons: true,
  });

  if (result.isConfirmed) {
    return true;
  } else {
    return false;
  }
};
