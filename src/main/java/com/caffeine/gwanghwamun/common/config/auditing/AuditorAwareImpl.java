package com.caffeine.gwanghwamun.common.config.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        // TODO: Spring Security 구현 후 주석 해제
//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//
//        return Optional.of(authentication.getName());

        // 임시: Spring Security 미구현으로 고정값 사용
        return Optional.of("Test");
    }
}
