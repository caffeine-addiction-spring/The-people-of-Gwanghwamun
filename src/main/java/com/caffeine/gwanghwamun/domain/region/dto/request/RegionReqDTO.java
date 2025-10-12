package com.caffeine.gwanghwamun.domain.region.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegionReqDTO {
	@NotBlank(message = "지역명은 비어 있을 수 없습니다.")
	private String name;
}
