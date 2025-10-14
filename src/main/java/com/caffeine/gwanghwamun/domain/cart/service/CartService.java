package com.caffeine.gwanghwamun.domain.cart.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
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
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
		Menu menu =
				menuRepository
						.findById(req.menuId())
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

		//        MenuOption option = null;
		//        if (menuOptionId != null) {
		//            option = menuOptionRepository.findById(req.menuOptionId())
		//                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴 옵션입니다."));
		//        }

		return null;
	}
}
