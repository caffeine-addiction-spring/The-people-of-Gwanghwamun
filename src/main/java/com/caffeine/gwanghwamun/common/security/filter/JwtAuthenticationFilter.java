package com.caffeine.gwanghwamun.common.security.filter;

import com.caffeine.gwanghwamun.common.jwt.JwtProvider;
import com.caffeine.gwanghwamun.common.jwt.JwtUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.domain.user.dto.request.LoginReqDTO;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtProvider jwtProvider;

	public JwtAuthenticationFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		setFilterProcessesUrl("/v1/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			LoginReqDTO requestDto =
					new ObjectMapper().readValue(request.getInputStream(), LoginReqDTO.class);

			return getAuthenticationManager()
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									requestDto.email(), requestDto.password(), null));
		} catch (IOException e) {
			log.error("로그인 요청 파싱 오류: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult) {
		String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
		UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

		String token = jwtProvider.createToken(email, role);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

		log.info("JWT 발급 완료: {}", email);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
