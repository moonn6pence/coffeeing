package com.ssafy.coffeeing.modules.member.domain;

import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Entity
public class Member extends BaseEntity {

	@Column(length = 320, nullable = false)
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
}
