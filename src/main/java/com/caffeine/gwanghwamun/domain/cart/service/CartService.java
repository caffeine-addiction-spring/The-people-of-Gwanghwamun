package com.caffeine.gwanghwamun.domain.cart.service;

import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartResDTO;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartRepository cartRepository;
	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	//    private final MenuOptionRepository menuOptionRepository;

	public SaveCartResDTO saveCart(long userId, SaveCartReqDTO req) {
		Store store =
				storeRepository
						.findById(req.storeId())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));
		Menu menu =
				menuRepository
						.findById(req.menuId())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

		//        MenuOption option = null;
		//        if (menuOptionId != null) {
		//            option = menuOptionRepository.findById(req.menuOptionId())
		//                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴 옵션입니다."));
		//        }

		return null;
	}
}
