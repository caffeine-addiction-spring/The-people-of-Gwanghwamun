package com.caffeine.gwanghwamun.common.config;

import com.caffeine.gwanghwamun.domain.user.jwt.JwtUtil;
import com.caffeine.gwanghwamun.domain.user.security.JwtAuthenticationFilter;
import com.caffeine.gwanghwamun.domain.user.security.JwtAuthorizationFilter;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 활성화
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable());

		http.sessionManagement(
				(sessionManagement) ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(
						(authorizeHttpRequests) ->
								authorizeHttpRequests
										.requestMatchers("/v1/auth/**")
										.permitAll()
										.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**")
										.permitAll()
										.requestMatchers("/v1/test/public")
										.permitAll()
										.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
										.permitAll()
										.anyRequest()
										.authenticated())
				.formLogin(form -> form.disable())
				.httpBasic(httpBasic -> httpBasic.disable());

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

		return http.build();
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
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}
}
