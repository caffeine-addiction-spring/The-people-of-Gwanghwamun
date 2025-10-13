package com.caffeine.gwanghwamun.domain.address.dto.response;

import com.caffeine.gwanghwamun.domain.address.entity.Address;
import java.util.UUID;

public record GetAddressListResDTO(
		UUID addressId,
		String address,
		String label,
		String phone,
		String recipient,
		String postalCode,
		boolean isDefault) {
	public static GetAddressListResDTO from(Address address) {
		return new GetAddressListResDTO(
				address.getAddressId(),
				address.getAddress(),
				address.getLabel(),
				address.getPhone(),
				address.getRecipient(),
				address.getPostalCode(),
				address.isDefault());
	}
}
