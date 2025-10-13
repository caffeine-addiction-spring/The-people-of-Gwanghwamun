package com.caffeine.gwanghwamun.domain.region.entity;

import com.caffeine.gwanghwamun.domain.store.entity.Store;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_delivery_addresses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID deliveryAddressId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;
}
