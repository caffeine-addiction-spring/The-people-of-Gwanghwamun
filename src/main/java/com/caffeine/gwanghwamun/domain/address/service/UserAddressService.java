package com.caffeine.gwanghwamun.domain.address.service;

import static com.caffeine.gwanghwamun.common.exception.ErrorCode.*;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.domain.address.dto.request.CreateUserAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.request.UpdateUserAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.response.DeleteUserAddressResDTO;
import com.caffeine.gwanghwamun.domain.address.dto.response.GetUserAddressListResDTO;
import com.caffeine.gwanghwamun.domain.address.entity.UserAddress;
import com.caffeine.gwanghwamun.domain.address.repository.UserAddressRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddressService {

	private final UserAddressRepository userAddressRepository;
	private final UserRepository userRepository;

	@Transactional
	public void createAddress(User authenticatedUser, CreateUserAddressReqDTO requestDto) {
		User user = getUserOrThrow(authenticatedUser);

		if (requestDto.isDefault() != null && requestDto.isDefault()) {
			userAddressRepository
					.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
					.ifPresent(UserAddress::unsetDefault);
		}

		userAddressRepository.save(requestDto.toAddress(user));
	}

	@Transactional
	public void updateAddress(
			User authenticatedUser, UUID addressId, UpdateUserAddressReqDTO requestDto) {
		User user = getUserOrThrow(authenticatedUser);
		UserAddress userAddress = getAddressOrThrow(user, addressId);

		if (requestDto.isDefault() != null && requestDto.isDefault() && !userAddress.isDefault()) {
			userAddressRepository
					.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
					.ifPresent(UserAddress::unsetDefault);
		}

		userAddress.update(
				requestDto.address(),
				requestDto.label(),
				requestDto.phone(),
				requestDto.recipient(),
				requestDto.postalCode(),
				requestDto.isDefault());
	}

	@Transactional
	public DeleteUserAddressResDTO deleteAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		UserAddress userAddress = getAddressOrThrow(user, addressId);

		if (userAddress.isDeleted()) {
			throw new CustomException(ALREADY_DELETED_ADDRESS);
		}

		userAddress.markAsDeleted();
		return new DeleteUserAddressResDTO(userAddress.getAddressId(), userAddress.getDeletedAt());
	}

	@Transactional(readOnly = true)
	public List<GetUserAddressListResDTO> getUserAddresses(User authenticatedUser) {
		User user = getUserOrThrow(authenticatedUser);
		return userAddressRepository.findAllByUserAndDeletedAtIsNullOrderByIsDefaultDescCreateAtDesc(user).stream()
				.map(GetUserAddressListResDTO::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public GetUserAddressListResDTO getUserAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		UserAddress userAddress = getAddressOrThrow(user, addressId);
		return GetUserAddressListResDTO.from(userAddress);
	}

	@Transactional
	public void setDefaultAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		UserAddress newDefault = getAddressOrThrow(user, addressId);

		if (newDefault.isDeleted()) {
			throw new CustomException(ADDRESS_NOT_FOUND);
		}

		userAddressRepository
				.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
				.filter(current -> !current.equals(newDefault))
				.ifPresent(UserAddress::unsetDefault);

		newDefault.setDefault();
	}

	private User getUserOrThrow(User authenticatedUser) {
		return userRepository
				.findById(authenticatedUser.getUserId())
				.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
	}

	private UserAddress getAddressOrThrow(User user, UUID addressId) {
		UserAddress userAddress =
				userAddressRepository
						.findById(addressId)
						.orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));
		if (!userAddress.getUser().equals(user)) {
			throw new CustomException(FORBIDDEN);
		}
		return userAddress;
	}
}
