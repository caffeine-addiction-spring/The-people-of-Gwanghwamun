package com.caffeine.gwanghwamun.domain.user.service;

import static com.caffeine.gwanghwamun.common.exception.ErrorCode.DUPLICATED;

import com.caffeine.gwanghwamun.common.exception.CustomException;
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
		userRepository
				.findByEmail(requestDto.email())
				.ifPresent(
						user -> {
							throw new CustomException(DUPLICATED);
						});

		userRepository.save(requestDto.toUser());
	}
}
