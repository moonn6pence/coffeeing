package com.ssafy.coffeeing.modules.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Member implements OAuth2User {

    private String oauthIdentifier;
    private String accessToken;
    private String refreshToken;
    private String grantType;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return this.oauthIdentifier;
    }
}
