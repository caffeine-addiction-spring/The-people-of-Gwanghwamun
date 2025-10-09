package com.caffeine.gwanghwamun.domain.store.entity;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_Stores")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID storeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// @Column(nullable = false)
	private Long groupId;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false, length = 255)
	private String address;

	@Column(nullable = false, length = 20)
	private String phone;

	@Column(length = 255)
	private String content;

	@Column(nullable = false)
	private Integer minDeliveryPrice = 0;

	@Column(nullable = false)
	private Integer deliveryTip = 0;

	@Column(nullable = false, precision = 2, scale = 1)
	private BigDecimal rating = BigDecimal.ZERO;

	@Column(nullable = false)
	private Integer reviewCount = 0;

	private String operationHours;

	private String closedDays;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private StoreCategoryEnum storeCategory;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
