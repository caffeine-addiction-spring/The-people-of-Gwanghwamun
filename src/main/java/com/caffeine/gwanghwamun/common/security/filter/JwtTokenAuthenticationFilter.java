package com.caffeine.gwanghwamun.common.security.filter;

import com.caffeine.gwanghwamun.common.jwt.JwtProvider;
import com.caffeine.gwanghwamun.common.jwt.JwtUtil;
import com.caffeine.gwanghwamun.common.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final JwtProvider jwtProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {

		String tokenValue = jwtUtil.getJwtFromHeader(req);
		log.info("요청 URI: {}", req.getRequestURI());

		if (!StringUtils.hasText(tokenValue)) {
			log.info("JWT 검증 실패: 토큰 없음");
			filterChain.doFilter(req, res);
			return;
		}

		try {
			jwtProvider.validateToken(tokenValue);

			Claims claims = jwtProvider.getUserInfoFromToken(tokenValue);
			String email = claims.getSubject();

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.info("JWT 인증 완료: {}, 권한: {}", email, userDetails.getAuthorities());

		} catch (Exception e) {
			log.error("JWT 인증 실패: {}", e.getMessage());
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(req, res);
	}
}
