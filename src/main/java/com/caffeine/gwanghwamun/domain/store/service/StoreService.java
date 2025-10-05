package com.caffeine.gwanghwamun.domain.store.service;

import com.caffeine.gwanghwamun.domain.store.dto.request.StoreCreateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreCreateResDTO;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	public StoreCreateResDTO createStore(StoreCreateReqDTO req, User user) {
		Store store = new Store();
		store.setName(req.getName());
		store.setAddress(req.getAddress());
		store.setPhone(req.getPhone());
		store.setStoreCategory(req.getStoreCategory());
		store.setContent(req.getContent());
		store.setMinDeliveryPrice(req.getMinDeliveryPrice() != null ? req.getMinDeliveryPrice() : 0);
		store.setDeliveryTip(req.getDeliveryTip() != null ? req.getDeliveryTip() : 0);
		store.setOperationHours(req.getOperationHours());
		store.setClosedDays(req.getClosedDays());

		store.setUserId(user.getUserId());
		store.setRating(BigDecimal.ZERO);
		store.setReviewCount(0);

		storeRepository.save(store);

		return new StoreCreateResDTO(store.getStoreId(), store.getName(), store.getStoreCategory());
	}
}
