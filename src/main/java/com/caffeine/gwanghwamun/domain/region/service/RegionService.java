package com.caffeine.gwanghwamun.domain.region.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.region.dto.request.RegionReqDTO;
import com.caffeine.gwanghwamun.domain.region.dto.response.RegionResDTO;
import com.caffeine.gwanghwamun.domain.region.entity.Address;
import com.caffeine.gwanghwamun.domain.region.repository.RegionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	@Transactional(readOnly = true)
	public List<RegionResDTO> getAllRegion(String direction) {
		Sort.Direction sortDirection =
				direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		List<Address> addressList = regionRepository.findAll(Sort.by(sortDirection, "name"));

		return addressList.stream()
				.map(address -> new RegionResDTO(address.getAddressId(), address.getName()))
				.toList();
	}

	@Transactional
	public RegionResDTO createRegion(RegionReqDTO request) {
		if (regionRepository.existsByName(request.getName())) {
			throw new CustomException(ErrorCode.REGION_DUPLICATED);
		}
		Address address = Address.create(request.getName());
		regionRepository.save(address);

		return new RegionResDTO(address.getAddressId(), address.getName());
	}

	@Transactional
	public RegionResDTO updateRegion(UUID regionId, RegionReqDTO request) {
		Address address =
				regionRepository
						.findById(regionId)
						.orElseThrow(() -> new CustomException(ErrorCode.REGION_NOT_FOUND));
		if (regionRepository.existsByName(request.getName())) {
			throw new CustomException(ErrorCode.REGION_DUPLICATED);
		}
		address.setName(request.getName());
		regionRepository.save(address);

		return new RegionResDTO(address.getAddressId(), address.getName());
	}

	@Transactional
	public void deleteRegion(UUID addressId) {
		Address region =
				regionRepository
						.findById(addressId)
						.orElseThrow(() -> new CustomException(ErrorCode.REGION_NOT_FOUND));

		regionRepository.delete(region);
	}
}
