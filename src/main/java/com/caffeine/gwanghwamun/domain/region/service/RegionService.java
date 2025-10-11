package com.caffeine.gwanghwamun.domain.region.service;

import com.caffeine.gwanghwamun.domain.region.dto.RegionListResDTO;
import com.caffeine.gwanghwamun.domain.region.entity.Address;
import com.caffeine.gwanghwamun.domain.region.repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	public List<RegionListResDTO> getAllRegion() {
		List<Address> addressList = regionRepository.findAll();

		return addressList.stream()
				.map(address -> new RegionListResDTO(address.getAddressId(), address.getName()))
				.toList();
	}
}
