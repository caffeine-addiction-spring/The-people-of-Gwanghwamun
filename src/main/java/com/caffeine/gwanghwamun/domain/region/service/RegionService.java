package com.caffeine.gwanghwamun.domain.region.service;

import com.caffeine.gwanghwamun.domain.region.dto.request.RegionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.region.dto.response.RegionResDTO;
import com.caffeine.gwanghwamun.domain.region.entity.Address;
import com.caffeine.gwanghwamun.domain.region.repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	public List<RegionResDTO> getAllRegion() {
		List<Address> addressList = regionRepository.findAll();

		return addressList.stream()
				.map(address -> new RegionResDTO(address.getAddressId(), address.getName()))
				.toList();
	}

	public RegionResDTO createRegion(RegionCreateReqDTO request) {
		Address address = Address.create(request.getName());
		regionRepository.save(address);

		return new RegionResDTO(address.getAddressId(), address.getName());
	}
}
