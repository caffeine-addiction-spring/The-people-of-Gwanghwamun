package com.caffeine.gwanghwamun.domain.region.service;

import com.caffeine.gwanghwamun.domain.region.dto.request.RegionReqDTO;
import com.caffeine.gwanghwamun.domain.region.dto.response.RegionResDTO;
import com.caffeine.gwanghwamun.domain.region.entity.Address;
import com.caffeine.gwanghwamun.domain.region.repository.RegionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public RegionResDTO createRegion(RegionReqDTO request) {
		Address address = Address.create(request.getName());
		regionRepository.save(address);

		return new RegionResDTO(address.getAddressId(), address.getName());
	}

	@Transactional
	public RegionResDTO updateRegion(UUID regionId, RegionReqDTO request) {
		Address address = regionRepository.getReferenceById(regionId);
		address.setName(request.getName());
		regionRepository.save(address);

		return new RegionResDTO(address.getAddressId(), address.getName());
	}
}
