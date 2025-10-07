package com.caffeine.gwanghwamun.domain.user.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public User(String email, String password, String name, String phone, UserRoleEnum role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.role = role;
	}

	public void update(String name, String phone) {
		if (name != null && !name.isBlank()) this.name = name;
		if (phone != null && !phone.isBlank()) this.phone = phone;
	}

	public void updatePassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	public void markAsDeleted() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}
