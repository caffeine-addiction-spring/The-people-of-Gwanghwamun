package com.caffeine.gwanghwamun.domain.user.address.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeleteUserAddressResDTO(UUID addressId, LocalDateTime deletedAt) {}
