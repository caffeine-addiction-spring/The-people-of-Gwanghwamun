package com.caffeine.gwanghwamun.domain.user.service;

import static com.caffeine.gwanghwamun.common.exception.ErrorCode.DUPLICATED;
import static com.caffeine.gwanghwamun.common.exception.ErrorCode.USER_NOT_FOUND;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.domain.user.dto.SignupReqDTO;
import com.caffeine.gwanghwamun.domain.user.dto.UserInfoResDTO;
import com.caffeine.gwanghwamun.domain.user.dto.UserInfoUpdateResDTO;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signUp(SignupReqDTO requestDto) {
		userRepository
				.findByEmail(requestDto.email())
				.ifPresent(
						user -> {
							throw new CustomException(DUPLICATED);
						});
		String encodedPassword = passwordEncoder.encode(requestDto.password());
		userRepository.save(requestDto.toUser(encodedPassword));
	}

	public UserInfoResDTO findUserInfo(User user) {
		userRepository
				.findById(user.getUserId())
				.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		return UserInfoResDTO.from(user);
	}

	@Transactional
	public UserInfoResDTO updateUserInfo(User authenticatedUser, UserInfoUpdateResDTO requestDto) {
		User user =
				userRepository
						.findById(authenticatedUser.getUserId())
						.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		user.update(requestDto.name(), requestDto.phone());
		return UserInfoResDTO.from(user);
	}
}
