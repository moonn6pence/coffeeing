package com.ssafy.coffeeing.modules.member.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.coffeeing.modules.member.dto.ExistNickNameResponse;
import com.ssafy.coffeeing.modules.member.dto.OnboardRequest;
import com.ssafy.coffeeing.modules.member.dto.OnboardResponse;
import com.ssafy.coffeeing.modules.member.service.MemberService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/unique-nickname")
	public BaseResponse<ExistNickNameResponse> checkDuplicateNickname(@RequestParam String nickname) {
		return BaseResponse.<ExistNickNameResponse>builder()
			.data(memberService.checkDuplicateNickname(nickname))
			.build();
	}

	@PostMapping("/onboard")
	public BaseResponse<OnboardResponse> insertAdditionalMemberInfo(@Valid @RequestBody OnboardRequest onboardRequest) {

		return BaseResponse.<OnboardResponse>builder()
			.data(memberService.insertAdditionalMemberInfo(onboardRequest))
			.build();
	}


}

