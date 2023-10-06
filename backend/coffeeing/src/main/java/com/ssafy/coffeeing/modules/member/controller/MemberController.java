package com.ssafy.coffeeing.modules.member.controller;

import javax.validation.Valid;

import com.ssafy.coffeeing.modules.member.dto.*;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "닉네임 중복 검사 요청")
    public BaseResponse<ExistNickNameResponse> checkDuplicateNickname(@RequestParam String nickname) {
        return BaseResponse.<ExistNickNameResponse>builder()
                .data(memberService.checkDuplicateNickname(nickname))
                .build();
    }

    @PostMapping("/onboard")
    @ApiOperation(value = "온보딩 시, 멤버 추가 정보 기입 요청")
    public BaseResponse<OnboardResponse> insertAdditionalMemberInfo(@Valid @RequestBody OnboardRequest onboardRequest) {

        return BaseResponse.<OnboardResponse>builder()
                .data(memberService.insertAdditionalMemberInfo(onboardRequest))
                .build();
    }

    @GetMapping("/info/{memberId}")
    @ApiOperation(value = "멤버 정보 요청")
    public BaseResponse<MemberInfoResponse> getMemberInfo(
            @PathVariable @NumberFormat Long memberId
    ) {
        return BaseResponse.<MemberInfoResponse>builder()
                .data(memberService.getMemberInfo(memberId))
                .build();
    }

    @GetMapping("/experience/{memberId}")
    @ApiOperation(value = "멤버 경험치, 레벨, 레벨업까지 경험치량 요청")
    public BaseResponse<ExperienceInfoResponse> getMemberExperience(@PathVariable @NumberFormat Long memberId) {
        return BaseResponse.<ExperienceInfoResponse>builder()
                .data(memberService.getMemberExperience(memberId))
                .build();
    }

    @PutMapping("/profile")
    @ApiOperation(value = "멤버 프로필 이미지 업데이트 요청")
    public BaseResponse<Void> updateMemberProfileImage(@Valid @RequestBody ProfileImageChangeRequest profileImageChangeRequest) {
        memberService.updateMemberProfileImage(profileImageChangeRequest);
        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/nickname")
    @ApiOperation(value = "멤버 닉네임 업데이트 요청")
    public BaseResponse<Void> updateMemberNickname(@Valid @RequestBody NicknameChangeRequest nicknameChangeRequest) {
        memberService.updateMemberNickname(nicknameChangeRequest);
        return BaseResponse.<Void>builder().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "page",
                    value = "페이지 번호",
                    required = true,
                    dataType = "Integer",
                    paramType = "query",
                    defaultValue = ""
            )
    })
    @GetMapping("/coffee/bookmark/{memberId}")
    @ApiOperation(value = "멤버가 북마크한 원두 리스트 요청")
    public BaseResponse<CoffeeBookmarkResponse> getBookmarkCoffees(@PathVariable @NumberFormat Long memberId,
                                                             @Valid @ModelAttribute PageInfoRequest pageInfoRequest) {
        return BaseResponse.<CoffeeBookmarkResponse>builder()
                .data(coffeeService.getBookmarkedCoffees(memberId, pageInfoRequest))
                .build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "page",
                    value = "페이지 번호",
                    required = true,
                    dataType = "Integer",
                    paramType = "query",
                    defaultValue = ""
            )
    })
    @GetMapping("/capsule/bookmark/{memberId}")
    @ApiOperation(value = "멤버가 북마크한 캡슐 리스트 요청")
    public BaseResponse<CapsuleBookmarkResponse> getBookmarkCapsules(@PathVariable @NumberFormat Long memberId,
                                                              @Valid @ModelAttribute PageInfoRequest pageInfoRequest) {
        return BaseResponse.<CapsuleBookmarkResponse>builder()
                .data(capsuleService.getBookmarkedCapsule(memberId, pageInfoRequest))
                .build();
    }

    @GetMapping("/my-info")
    @ApiOperation(value = "나의 정보 요청")
    public BaseResponse<MyInfoResponse> getMyInfo() {
        return BaseResponse.<MyInfoResponse>builder()
                .data(memberService.getCurrentMemberInfo())
                .build();
    }
}

