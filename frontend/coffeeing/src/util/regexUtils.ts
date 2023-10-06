export type ValidateNicknameResult = {
  isValid: boolean;
  message: string;
};

/**
 * 닉네임 형식이 사용가능한지 확인한다
 * - 공백  /^(?!\s)(?!.*\s$)(?!.*\s{2}).*$/
 * - 문자 종류  /^[\w\s가-힣ㄱ-ㅎ]*$/
 * - 길이 1~11
 * @param candidate 확인할 닉네임 입력
 *
 */
export const validateNickname = (candidate: string): ValidateNicknameResult => {
  const spaces = /^(?!\s)(?!.*\s$)(?!.*\s{2}).*$/;
  if (!spaces.test(candidate)) {
    return {
      isValid: false,
      message: '공백으로 시작, 끝이 불가하며 2개 이상의 공백을 연달아 사용할 수 없습니다.',
    };
  }

  const characters = /^[\w\s가-힣ㄱ-ㅎ]*$/;
  if (!characters.test(candidate)) {
    return {
      isValid: false,
      message: '한글, 영문, 숫자, _, 공백만 사용할 수 있습니다.',
    };
  }

  if (candidate.length < 1 || candidate.length > 11) {
    return {
      isValid: false,
      message: '닉네임은 1글자 이상, 11글자 이하이어야 합니다.',
    };
  }

  return {
    isValid: true,
    message: '사용가능합니다',
  };
};
