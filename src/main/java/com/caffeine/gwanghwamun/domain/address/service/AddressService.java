package com.caffeine.gwanghwamun.domain.address.service;

import static com.caffeine.gwanghwamun.common.exception.ErrorCode.USER_NOT_FOUND;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.domain.address.dto.request.CreateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.repository.AddressRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	@Transactional
	public void createAddress(User authenticatedUser, CreateAddressReqDTO requestDto) {
		User user =
				userRepository
						.findById(authenticatedUser.getUserId())
						.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		addressRepository.save(requestDto.toAddress(user));
	}
}
