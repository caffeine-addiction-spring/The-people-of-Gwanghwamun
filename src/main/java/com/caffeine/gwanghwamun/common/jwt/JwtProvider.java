package com.caffeine.gwanghwamun.common.jwt;

import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "JwtTokenProvider")
@Component
@RequiredArgsConstructor
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	public static final String AUTHORIZATION_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String createToken(String username, UserRoleEnum role) {
		Date date = new Date();

		return BEARER_PREFIX
				+ Jwts.builder()
						.setSubject(username)
						.claim(AUTHORIZATION_KEY, role)
						.setExpiration(new Date(date.getTime() + jwtProperties.getExpirationTime()))
						.setIssuedAt(date)
						.signWith(key, signatureAlgorithm)
						.compact();
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
		}
	}

	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
