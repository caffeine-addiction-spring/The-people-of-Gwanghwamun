package com.caffeine.gwanghwamun.domain.user.security;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import com.caffeine.gwanghwamun.domain.user.jwt.JwtUtil;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private final String TEST_EMAIL = "test@email.com";
    private final String TEST_PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email(TEST_EMAIL)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .name("TestUser")
                .phone("01012345678")
                .role(UserRoleEnum.CUSTOMER)
                .build();
        userRepository.save(user);
    }

    private String getAuthToken() throws Exception {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", TEST_EMAIL);
        loginRequest.put("password", TEST_PASSWORD);

        MvcResult loginResult = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        return loginResult.getResponse().getHeader(JwtUtil.AUTHORIZATION_HEADER);
    }


    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        // Given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", TEST_EMAIL);
        loginRequest.put("password", TEST_PASSWORD);

        // When & Then
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(JwtUtil.AUTHORIZATION_HEADER));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
    void loginFailWithWrongPassword() throws Exception {
        // Given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", TEST_EMAIL);
        loginRequest.put("password", "wrongpassword");

        // When & Then
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized()); // 401: 인증 실패
    }

    @Test
    @DisplayName("JWT 토큰으로 보호된 리소스 접근 테스트 (성공)")
    void authenticatedRequestWithJwt() throws Exception {
        // Given
        String token = getAuthToken();

        // When & Then - 토큰으로 /v1/test/protected 접근 (인증된 사용자라면 접근 허용)
        mockMvc.perform(get("/v1/test/protected")
                        .header(JwtUtil.AUTHORIZATION_HEADER, token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("JWT 토큰 없이 보호된 리소스 접근 테스트 (실패)")
    void accessProtectedResourceWithoutToken() throws Exception {
        // When & Then - 토큰 없이 /v1/test/protected 접근 (anyRequest().authenticated() 에 의해 403 반환 예상)
        mockMvc.perform(get("/v1/test/protected"))
                .andDo(print())
                .andExpect(status().isForbidden()); // 403: 접근 거부
    }

    @Test
    @DisplayName("유효하지 않은 JWT 토큰으로 접근 테스트 (실패)")
    void accessWithInvalidToken() throws Exception {
        // Given
        String invalidToken = "Bearer invalid.token.here";

        // When & Then
        mockMvc.perform(get("/v1/test/protected")
                        .header(JwtUtil.AUTHORIZATION_HEADER, invalidToken))
                .andDo(print())
                .andExpect(status().isForbidden()); // 403: 인증 실패 후 접근 거부
    }

    @Test
    @DisplayName("공개 엔드포인트 접근 테스트 (토큰 불필요)")
    void accessPublicEndpoint() throws Exception {
        // When & Then - /v1/test/public 엔드포인트 접근 (permitAll() 설정)
        mockMvc.perform(get("/v1/test/public"))
                .andDo(print())
                .andExpect(status().isOk()); // 200: 접근 허용
    }

    @Test
    @DisplayName("권한 확인 테스트 - CUSTOMER 권한으로 CUSTOMER 전용 접근 (성공)")
    void checkCustomerAuthoritySuccess() throws Exception {
        // Given
        String token = getAuthToken();

        // When & Then - /v1/test/customer 접근 (@PreAuthorize("hasRole('CUSTOMER')"))
        mockMvc.perform(get("/v1/test/customer")
                        .header(JwtUtil.AUTHORIZATION_HEADER, token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한 확인 테스트 - CUSTOMER 권한으로 MANAGER 전용 접근 (실패)")
    void checkCustomerAuthorityFail() throws Exception {
        // Given
        String token = getAuthToken();

        // When & Then - /v1/test/manager 접근 (@PreAuthorize("hasRole('MANAGER')"))
        mockMvc.perform(get("/v1/test/manager")
                        .header(JwtUtil.AUTHORIZATION_HEADER, token))
                .andDo(print())
                .andExpect(status().isForbidden()); // 403: 권한 부족
    }
}
