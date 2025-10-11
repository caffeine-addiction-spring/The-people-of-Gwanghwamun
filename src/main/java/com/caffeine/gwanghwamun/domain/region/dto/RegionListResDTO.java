package com.caffeine.gwanghwamun.domain.region.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionListResDTO {
	private UUID regionId;
	private String name;
}
