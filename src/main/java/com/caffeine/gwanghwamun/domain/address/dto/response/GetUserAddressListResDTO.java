package com.caffeine.gwanghwamun.domain.address.dto.response;

import com.caffeine.gwanghwamun.domain.address.entity.UserAddress;
import java.util.UUID;

public record GetUserAddressListResDTO(
		UUID addressId,
		String address,
		String label,
		String phone,
		String recipient,
		String postalCode,
		boolean isDefault) {
	public static GetUserAddressListResDTO from(UserAddress userAddress) {
		return new GetUserAddressListResDTO(
				userAddress.getAddressId(),
				userAddress.getAddress(),
				userAddress.getLabel(),
				userAddress.getPhone(),
				userAddress.getRecipient(),
				userAddress.getPostalCode(),
				userAddress.isDefault());
	}
}
