import React,{useState, useEffect} from "react";

import { useDispatch } from "react-redux";
import { AppDispatch  } from 'store/store';

import Button from "components/Button";
import { TextGridSelector } from "components/Form/TextGridSelectForm"
import { AGE_ITEMS, GENDER_ITEMS } from "util/constants";
import { getMyInfo, checkUniqueNickname, postOnboard } from "service/member/member"
import { MemberState } from "service/member/types";
import { setMyInfo } from "store/memberSlice";

export const AfterSignupPage = () => {
  const dispatch = useDispatch<AppDispatch>();

  const [nickname, setNickname] = useState<string>("");
  const [selectedGender, setSelectedGender] = useState({
    value: -1,
    name: "미선택"
  });
  const [selectedAge, setSelectedAge] = useState({
    value: -1,
    name: "미선택"
  });

  useEffect(()=>{
    const checkValidMemberInfo = async()=>{
        const result = await getMyInfo();
        if(!result || result.state != MemberState.BEFORE_ADDITIONAL_DATA) {
            window.location.replace("/");
        }
    }
    checkValidMemberInfo();
  }, [])

  const handleCheckNickname = async () => {
      if(nickname.length <=0 || nickname.length > 11) {
          // TODO ADD MODAL
          return;
      }
      const result = await checkUniqueNickname(nickname);
      if(result) {
          alert(result.exist);
      }
  }

  const handleSubmit = async () => {
    if(nickname.length <=0 || nickname.length > 11) {
        // TODO ADD MODAL
        return;
    }

    if(selectedAge.value == -1 || selectedGender.value == -1) {
        // TODO ADD MODAL
        return;
    }

    const result = await postOnboard({
        nickname: nickname,
        ageIdx: selectedAge.value,
        genderIdx: selectedGender.value
    });

    if(result) {
        const myInfo = await getMyInfo();
        if(myInfo) {
            dispatch(setMyInfo(myInfo));
        }
        window.location.replace("/");
    }
  }
  return(
    <div className="absolute">
        <div className="flex flex-col gap-6 items-center pt-10">
        <div className="text-3xl font-bold pb-10">내 정보 등록하기</div>
        
        <div className="form-container flex flex-col gap-6 items-center">
            <div className="nickname-container w-96 flex flex-col gap-1">
                <label className="text-base text-gray-800 font-medium">닉네임</label>
                <div className="nickname-input-form w-96 flex flex-row gap-2">
                    <div className="input-form flex flex-col w-2/3">
                        <input
                            className="h-14 border border-gray-400 pl-4 rounded-xl"
                            type="text"
                            placeholder="닉네임을 입력해주세요."
                            value={nickname}
                            onChange={(e)=>{setNickname(e.target.value)}}/>
                    </div>

                    <div className="w-1/3">
                        <button 
                            className="w-full h-14 text-white font-bold rounded-xl bg-light-roasting"
                            onClick={handleCheckNickname}>
                            중복 확인
                        </button>
                    </div>
                </div>
            </div>

            <TextGridSelector 
                label={"성별"}
                selectedItem={selectedGender}
                setSelectedItem={setSelectedGender}
                itemList={GENDER_ITEMS}
                containerWidth={"w-96"}
                gridColumnStyle={"grid-cols-2"}
            />

            <TextGridSelector 
                label={"나이"}
                selectedItem={selectedAge}
                setSelectedItem={setSelectedAge}
                itemList={AGE_ITEMS}
                containerWidth={"w-96"}
                gridColumnStyle={"grid-cols-3"}
            />

            <div className="flex flex-col items-center gap-2">
                <Button placeholder="등록하기" handleSubmit={handleSubmit}
                color="bg-light-roasting"/>
            </div>
        </div>
        </div>
    </div>
  )
}