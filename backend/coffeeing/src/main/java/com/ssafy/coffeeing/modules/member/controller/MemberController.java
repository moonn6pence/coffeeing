package com.ssafy.coffeeing.modules.member.controller;

import javax.validation.Valid;

import com.ssafy.coffeeing.modules.member.dto.*;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

import com.ssafy.coffeeing.modules.member.service.MemberService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final CoffeeService coffeeService;
    private final CapsuleService capsuleService;

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

    @GetMapping("/info/{memberId}")
    public BaseResponse<MemberInfoResponse> getMemberInfo(
            @PathVariable @NumberFormat Long memberId
    ) {
        return BaseResponse.<MemberInfoResponse>builder()
                .data(memberService.getMemberInfo(memberId))
                .build();
    }

    @GetMapping("/experience/{memberId}")
    public BaseResponse<ExperienceInfoResponse> getMemberExperience(@PathVariable @NumberFormat Long memberId) {
        return BaseResponse.<ExperienceInfoResponse>builder()
                .data(memberService.getMemberExperience(memberId))
                .build();
    }

    @PutMapping("/profile")
    public BaseResponse<Void> updateMemberProfileImage(@Valid @RequestBody ProfileImageChangeRequest profileImageChangeRequest) {
        memberService.updateMemberProfileImage(profileImageChangeRequest);
        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/nickname")
    public BaseResponse<Void> updateMemberNickname(@Valid @RequestBody NicknameChangeRequest nicknameChangeRequest){
        memberService.updateMemberNickname(nicknameChangeRequest);
        return BaseResponse.<Void>builder().build();
    }

    @GetMapping("/coffee/bookmark/{memberId}")
    public BaseResponse<BookmarkResponse> getBookmarkCoffees(@PathVariable @NumberFormat Long id,
                                                             @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<BookmarkResponse>builder()
                .data(coffeeService.getBookmarkedCoffees(id, pageInfoRequest))
                .build();
    }

    @GetMapping("/capsule/bookmark/{memberId}")
    public BaseResponse<BookmarkResponse> getBookmarkCapsules(@PathVariable @NumberFormat Long memberId,
                                                              @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<BookmarkResponse>builder()
                .data(capsuleService.getBookmarkedCapsule(memberId, pageInfoRequest))
                .build();
    }

    @GetMapping("/my-info")
    public BaseResponse<MyInfoResponse> getMyInfo() {
        return BaseResponse.<MyInfoResponse>builder()
            .data(memberService.getCurrentMemberInfo())
            .build();
    }
}

