package com.caffeine.gwanghwamun.domain.user.service;

import com.caffeine.gwanghwamun.domain.user.dto.SignupReqDTO;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void signUp(SignupReqDTO requestDto) {
		userRepository.save(requestDto.toUser());
	}
}
