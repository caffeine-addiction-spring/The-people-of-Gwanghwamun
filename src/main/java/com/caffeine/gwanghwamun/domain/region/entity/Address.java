package com.caffeine.gwanghwamun.domain.region.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_addresses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID addressId;

	@Column(nullable = false)
	private String name;

	public static Address create(String name) {
		Address address = new Address();
		address.setName(name);
		return address;
	}
}
