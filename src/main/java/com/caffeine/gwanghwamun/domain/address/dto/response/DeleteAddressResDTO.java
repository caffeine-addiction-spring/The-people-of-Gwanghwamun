package com.caffeine.gwanghwamun.domain.address.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeleteAddressResDTO(UUID addressId, LocalDateTime deletedAt) {}
