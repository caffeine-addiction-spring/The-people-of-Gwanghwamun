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
		User user = getUserOrThrow(authenticatedUser);

		if (requestDto.isDefault() != null && requestDto.isDefault()) {
			addressRepository.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
					.ifPresent(Address::unsetDefault);
		}

		addressRepository.save(requestDto.toAddress(user));
	}

	@Transactional
	public void updateAddress(User authenticatedUser, UUID addressId, UpdateAddressReqDTO requestDto) {
		User user = getUserOrThrow(authenticatedUser);
		Address address = getAddressOrThrow(user, addressId);

		if (requestDto.isDefault() != null && requestDto.isDefault() && !address.isDefault()) {
			addressRepository.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
					.ifPresent(Address::unsetDefault);
		}

		address.update(
				requestDto.address(),
				requestDto.label(),
				requestDto.phone(),
				requestDto.recipient(),
				requestDto.postalCode(),
				requestDto.isDefault()
		);
	}

	@Transactional
	public DeleteAddressResDTO deleteAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		Address address = getAddressOrThrow(user, addressId);

		if (address.isDeleted()) {
			throw new CustomException(ALREADY_DELETED_ADDRESS);
		}

		address.markAsDeleted();
		return new DeleteAddressResDTO(address.getAddressId(), address.getDeletedAt());
	}


	@Transactional(readOnly = true)
	public List<GetAddressListResDTO> getUserAddresses(User authenticatedUser) {
		User user = getUserOrThrow(authenticatedUser);
		return addressRepository.findAllByUserAndDeletedAtIsNull(user).stream()
				.map(GetAddressListResDTO::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public GetAddressListResDTO getUserAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		Address address = getAddressOrThrow(user, addressId);
		return GetAddressListResDTO.from(address);
	}


	@Transactional
	public void setDefaultAddress(User authenticatedUser, UUID addressId) {
		User user = getUserOrThrow(authenticatedUser);
		Address newDefault = getAddressOrThrow(user, addressId);

		if (newDefault.isDeleted()) {
			throw new CustomException(ADDRESS_NOT_FOUND);
		}

		addressRepository.findByUserAndIsDefaultTrueAndDeletedAtIsNull(user)
				.filter(current -> !current.equals(newDefault))
				.ifPresent(Address::unsetDefault);

		newDefault.setDefault();
	}

	private User getUserOrThrow(User authenticatedUser) {
		return userRepository.findById(authenticatedUser.getUserId())
				.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
	}

	private Address getAddressOrThrow(User user, UUID addressId) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));
		if (!address.getUser().equals(user)) {
			throw new CustomException(FORBIDDEN);
		}
		return address;
	}
}
