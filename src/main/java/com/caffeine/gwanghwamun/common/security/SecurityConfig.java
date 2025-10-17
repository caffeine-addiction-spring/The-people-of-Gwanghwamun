package com.caffeine.gwanghwamun.common.security;

import com.caffeine.gwanghwamun.common.jwt.JwtProvider;
import com.caffeine.gwanghwamun.common.jwt.JwtUtil;
import com.caffeine.gwanghwamun.common.security.filter.JwtLoginFilter;
import com.caffeine.gwanghwamun.common.security.filter.JwtTokenAuthenticationFilter;
import com.caffeine.gwanghwamun.common.security.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	private final JwtProvider jwtProvider;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(this::configureAuthorization)
				.exceptionHandling(this::configureExceptionHandling)
				.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.build();
	}

	private void configureAuthorization(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
					auth) {
		auth.requestMatchers("/v1/auth/**")
				.permitAll()
				.requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**")
				.permitAll()
				.requestMatchers("/v1/test/public")
				.permitAll()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll()
				.requestMatchers("/error")
				.permitAll()
				.anyRequest()
				.authenticated();
	}

	private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exception) {
		exception
				.authenticationEntryPoint(
						(req, res, ex) -> {
							res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							res.setContentType("application/json;charset=UTF-8");
							new ObjectMapper()
									.writeValue(
											res.getWriter(),
											Map.of(
													"error", "Unauthorized",
													"message", "인증이 필요합니다",
													"path", req.getRequestURI()));
						})
				.accessDeniedHandler(
						(req, res, ex) -> {
							res.setStatus(HttpServletResponse.SC_FORBIDDEN);
							res.setContentType("application/json;charset=UTF-8");
							new ObjectMapper()
									.writeValue(
											res.getWriter(),
											Map.of(
													"error", "Forbidden",
													"message", "접근 권한이 없습니다",
													"path", req.getRequestURI()));
						});
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
			throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public JwtLoginFilter jwtAuthenticationFilter() throws Exception {
		JwtLoginFilter filter = new JwtLoginFilter(jwtProvider);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public JwtTokenAuthenticationFilter jwtAuthorizationFilter() {
		return new JwtTokenAuthenticationFilter(jwtUtil, jwtProvider, userDetailsService);
	}
}
