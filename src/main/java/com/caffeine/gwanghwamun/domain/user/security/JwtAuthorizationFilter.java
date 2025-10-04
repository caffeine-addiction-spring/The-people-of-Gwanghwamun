package com.caffeine.gwanghwamun.domain.user.security;

import com.caffeine.gwanghwamun.domain.user.jwt.JwtUtil;
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
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {

		log.info(req.getRequestURI());

		String tokenValue = jwtUtil.getJwtFromHeader(req);

		if (StringUtils.hasText(tokenValue)) {
			try {
				if (jwtUtil.validateToken(tokenValue)) {
					Claims claims = jwtUtil.getUserInfoFromToken(tokenValue);
					String email = claims.getSubject();

					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication =
							new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authentication);
					log.info("JWT 인증 완료: {}, 권한: {}", email, userDetails.getAuthorities());
				}
			} catch (Exception e) {
				log.error("JWT 인증 과정 에러: {}", e.getMessage());
			}
		}

		filterChain.doFilter(req, res);
	}
}
