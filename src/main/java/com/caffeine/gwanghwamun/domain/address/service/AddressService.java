package com.caffeine.gwanghwamun.domain.address.service;

import static com.caffeine.gwanghwamun.common.exception.ErrorCode.*;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.domain.address.dto.request.CreateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.request.UpdateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.response.DeleteAddressResDTO;
import com.caffeine.gwanghwamun.domain.address.dto.response.GetAddressListResDTO;
import com.caffeine.gwanghwamun.domain.address.entity.Address;
import com.caffeine.gwanghwamun.domain.address.repository.AddressRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
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

	@Transactional
	public void updateAddress(
			User authenticatedUser, UUID addressId, UpdateAddressReqDTO requestDto) {
		User user =
				userRepository
						.findById(authenticatedUser.getUserId())
						.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		Address address =
				addressRepository
						.findById(addressId)
						.orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

		if (!address.getUser().equals(user)) {
			throw new CustomException(FORBIDDEN);
		}

		address.update(
				requestDto.address(),
				requestDto.label(),
				requestDto.phone(),
				requestDto.recipient(),
				requestDto.postalCode(),
				requestDto.isDefault());
	}

	@Transactional
	public DeleteAddressResDTO deleteAddress(User authenticatedUser, UUID addressId) {
		User user =
				userRepository
						.findById(authenticatedUser.getUserId())
						.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		Address address =
				addressRepository
						.findById(addressId)
						.orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

		if (!address.getUser().equals(user)) {
			throw new CustomException(FORBIDDEN);
		}

		if (address.isDeleted()) {
			throw new CustomException(ALREADY_DELETED_ADDRESS);
		}

		address.markAsDeleted();

		return new DeleteAddressResDTO(address.getAddressId(), address.getDeletedAt());
	}

	public List<GetAddressListResDTO> getUserAddresses(User authenticatedUser) {
		User user =
				userRepository
						.findById(authenticatedUser.getUserId())
						.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		return addressRepository.findAllByUserAndDeletedAtIsNull(user).stream()
				.map(GetAddressListResDTO::from)
				.toList();
	}
}
