package com.ssafy.coffeeing.modules.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ssafy.coffeeing.modules.global.security.filter.JWTExceptionHandleFilter;
import com.ssafy.coffeeing.modules.global.security.filter.JWTFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final OAuth2UserService oAuth2UserService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider)
            .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource);
        disableNotUseDefaultSecuritySetting(http);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTExceptionHandleFilter(), JWTFilter.class);

        http.authorizeRequests().anyRequest().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(oAuth2UserService)
            .and()
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler);

        return http.build();
    }

    /**
     *  Rest API 서버, 별도의 JWT 토큰을 Authorization Header, Bear Token을 이용해 인증, 인가 관리를 진행하므로,
     *  EnableWebSecurity 어노테이션으로 인해 생기는 불필요한 설정(httpBasic, default from login, csrf 설정)을 disable 한다.
     */
    private HttpSecurity disableNotUseDefaultSecuritySetting(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        return http;
    }
}