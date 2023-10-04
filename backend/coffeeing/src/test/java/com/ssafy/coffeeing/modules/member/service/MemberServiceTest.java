package com.ssafy.coffeeing.modules.member.service;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.dto.ExistNickNameResponse;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.MemberInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.NicknameChangeRequest;
import com.ssafy.coffeeing.modules.member.dto.OnboardRequest;
import com.ssafy.coffeeing.modules.member.dto.OnboardResponse;
import com.ssafy.coffeeing.modules.member.dto.ProfileImageChangeRequest;
import com.ssafy.coffeeing.modules.member.mapper.MemberMapper;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.member.util.MemberUtil;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RecordApplicationEvents
class MemberServiceTest extends ServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberUtil memberUtil;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @DisplayName("점수 증가시 저장된 점수가 증가한다.")
    @Test
    void Given_ExperienceRecord_When_AddExperienceEvent_Then_Success() {
        // given
        Member member = MemberTestDummy.createGeneralMember("닉네이무", "1234", "how@and.why");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ExperienceEvent(75, member.getId()));
        // then
        assertThat(member.getExperience()).isEqualTo(75);

    }

    @DisplayName("점수 증가시 레벨업을 실행하고 점수를 감소시킨다.")
    @Test
    void Given_ActivityConductedEvent_When_AddExperienceEvent_Then_Success() {
        // given
        Member member = MemberTestDummy.createGeneralMember("얍", "1234", "a@a.com");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ExperienceEvent(150, member.getId()));
        // then
        assertAll(
                () -> assertThat(member.getExperience()).isEqualTo(25),
                () -> assertThat(member.getMemberLevel()).isEqualTo(1)
        );
    }

    @DisplayName("마이페이지에서 회원정보 요청시 닉네임, 이미지만 반환한다.")
    @Test
    void Given_SecurityMemberContext_When_GetMemberInfo_Then_Success() {
        //given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        //when
        MemberInfoResponse memberBaseInfoResponse = memberService.getMemberInfo(generalMember.getId());

        //then
        assertThat(memberBaseInfoResponse).isEqualTo(
                MemberMapper.supplyBaseInfoResponseOf(generalMember, null)
        );
    }

    @DisplayName("멤버의 경험치, 레벨, 레벨업까지 필요 경험치량을 반환한다.")
    @Test
    void Given_Member_When_GetExperienceInfo_Then_Success() {
        // given
        memberService.addExperience(new ExperienceEvent(150, generalMember.getId()));
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ExperienceInfoResponse experienceInfoResponse = memberService.getMemberExperience(generalMember.getId());

        // then
        assertEquals(
                experienceInfoResponse,
                MemberMapper.supplyExperienceInfoResponseOf(
                        generalMember,
                        memberUtil.calculateLevelUpExperience(
                                generalMember.getMemberLevel()
                        )
                )
        );
    }

    @Test
    @DisplayName("멤버 최대 경험치 초과시 MAX LEVEL(3) 달성 및 최대 경험치 600을 유지한다.")
    void Given_Member_When_GetExperienceToMax_Then_Success() {
        // given
        memberService.addExperience(new ExperienceEvent(4500, generalMember.getId()));
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ExperienceInfoResponse experienceInfoResponse = memberService.getMemberExperience(generalMember.getId());
        System.out.println(generalMember.getMemberLevel());
        System.out.println(memberUtil.calculateLevelUpExperience(generalMember.getMemberLevel()));
        // then
        assertAll(
                () -> assertEquals(3, generalMember.getMemberLevel()),
                () -> assertEquals(600, generalMember.getExperience())
        );
    }

    @DisplayName("멤버의 프로필 이미지를 변경한다.")
    @Test
    void Given_MemberProfileImageUrl_When_UpdateMemberProfileImage_Then_Success() {
        // given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        String newProfileImageUrl = "http://some-aws-directory:I23m52A93g39e";
        // when
        memberService.updateMemberProfileImage(new ProfileImageChangeRequest(newProfileImageUrl));
        // then
        assertThat(generalMember.getProfileImage()).isEqualTo(newProfileImageUrl);
    }

    @DisplayName("멤버 닉네임을 변경한다.")
    @Test
    void Given_MemberNickname_When_UpdateMemberNickname_Then_Success() {
        // given
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        String newNickname = "제네럴 백";
        // when
        memberService.updateMemberNickname(new NicknameChangeRequest(newNickname));
        // then
        assertThat(generalMember.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("중복된 멤버 닉네임으로 변경을 시도하는 경우 변경은 실패한다.")
    @Test
    void Given_DuplicateMemberNickname_When_updateMemberNickname_Then_Fail() {
        // given
        Member preExistMember = MemberTestDummy.createMemberSean();
        memberRepository.save(preExistMember);
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        String newDuplicateNickname = preExistMember.getNickname();
        NicknameChangeRequest nicknameChangeRequest = new NicknameChangeRequest(newDuplicateNickname);
        // when then
        assertEquals(MemberErrorInfo.PRE_EXIST_NICKNAME,
                assertThrows(BusinessException.class, () -> memberService.updateMemberNickname(nicknameChangeRequest)).getInfo()
        );
    }

    @DisplayName("회원가입만 사용자의 경우 추가정보 입력을 통해 사용자 상태를 업데이트 한다.")
    @Test
    void Given_OnboardRequest_When_InsertAdditionalMemberInfo_Then_Success() {
        //given
        Member member = MemberTestDummy.createMember("dsfmkls", "{noop}test", "t1e1s1t@naver.com", MemberState.BEFORE_ADDITIONAL_DATA);
        memberRepository.save(member);
        OnboardRequest onboardRequest = new OnboardRequest("ad1f11n11", 0, 0);
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(member);
        //when
        OnboardResponse response = memberService.insertAdditionalMemberInfo(onboardRequest);
        //then
        assertAll(
                () -> assertEquals(member.getId(), response.memberId()),
                () -> assertEquals(member.getNickname(), response.nickname())
        );
    }

    @DisplayName("이미 추가정보를 입력한 사용자는 추가정보 입력을 할 수 없다.")
    @Test
    void Given_OnboardRequest_When_InsertAdditionalMemberInfo_Then_Fail() {
        //given
        OnboardRequest onboardRequest = new OnboardRequest("제약조건상절대중복이발생불가능한닉네임", 0, 0);
        BDDMockito.given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        //when, then
        assertEquals(MemberErrorInfo.NOT_VALID_STATE,
                assertThrows(BusinessException.class, () -> memberService.insertAdditionalMemberInfo(onboardRequest)).getInfo());
    }

    @DisplayName("중복되는 닉네임이 존재하는지 확인한다.")
    @Test
    void Given_MemberNickname_When_CheckDuplication_Then_ReturnTrue() {
        //given
        String existNickName = generalMember.getNickname();
        //when
        ExistNickNameResponse response = memberService.checkDuplicateNickname(existNickName);
        //then
        assertTrue(response.exist());
    }
}
