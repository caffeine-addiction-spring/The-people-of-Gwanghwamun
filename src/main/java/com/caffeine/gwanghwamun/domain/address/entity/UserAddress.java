package com.caffeine.gwanghwamun.domain.address.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user_addresses")
public class UserAddress extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "address_id", updatable = false, nullable = false)
	private UUID addressId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String label;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String recipient;

	@Column(nullable = false, name = "postal_code")
	private String postalCode;

	@Column(nullable = false, name = "is_default")
	private boolean isDefault = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public UserAddress(
			User user,
			String address,
			String label,
			String phone,
			String recipient,
			String postalCode,
			boolean isDefault) {
		this.user = user;
		this.address = address;
		this.label = label;
		this.phone = phone;
		this.recipient = recipient;
		this.postalCode = postalCode;
		this.isDefault = isDefault;
	}

	public void markAsDeleted() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}

	public void update(
			String address,
			String label,
			String phone,
			String recipient,
			String postalCode,
			Boolean isDefault) {
		if (address != null) this.address = address;
		if (label != null) this.label = label;
		if (phone != null) this.phone = phone;
		if (recipient != null) this.recipient = recipient;
		if (postalCode != null) this.postalCode = postalCode;
		if (isDefault != null) this.isDefault = isDefault;
	}

	public void setDefault() {
		this.isDefault = true;
	}

	public void unsetDefault() {
		this.isDefault = false;
	}
}
