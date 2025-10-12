package com.caffeine.gwanghwamun.domain.region.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionResDTO {
	private UUID regionId;
	private String name;
}
