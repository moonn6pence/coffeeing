package com.ssafy.coffeeing.modules.member.domain;

import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Entity
public class Member extends BaseEntity {

	@Column(length = 320, nullable = false, unique = true)
	private String email;

	@Column(length = 512, nullable = false)
	private String password;

	@Column(length = 64, nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberState state;

	@Column(length = 16)
	private String nickname;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Enumerated(EnumType.ORDINAL)
	private Age age;

	@Column(length = 512)
	private String profileImage;

	@Column(length = 512)
	private String oauthIdentifier;

	@Column
	@Builder.Default
	private Integer experience = 0;

	@Column
	@Builder.Default
	private Integer memberLevel = 0;

	public void updateByOnboardResult(String nickname, int ageIdx, int genderIdx) {
		this.nickname = nickname;
		this.age = Age.values()[ageIdx];
		this.gender = Gender.values()[genderIdx];
	}

	public void updateMemberState(MemberState state) {
		this.state = state;
	}

	public void addExperience(int experience){
		this.experience+=experience;
	}
	public void subtractExperience(int amount){
		if(this.experience>amount){
			this.experience-=amount;
		}
	}
	public void levelUp(){
		this.memberLevel+=1;
	}

}
