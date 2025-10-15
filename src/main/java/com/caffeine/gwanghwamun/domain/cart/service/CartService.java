package com.caffeine.gwanghwamun.domain.cart.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.dto.CartResDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartResDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.UpdateCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.cart.repository.CartRepository;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuOptionRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;
	private final MenuOptionRepository menuOptionRepository;

	@Transactional
	public SaveCartResDTO saveCart(Long userId, SaveCartReqDTO req) {

		Store store =
				storeRepository
						.findById(req.storeId())
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		Menu menu =
				menuRepository
						.findById(req.menuId())
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

		MenuOption option = null;
		if (req.menuOptionId() != null) {
			option =
					menuOptionRepository
							.findById(req.menuOptionId())
							.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
		}

		User user =
				userRepository
						.findById(userId)
						.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		if (menu.isSoldOut() || menu.isHidden()) {
			throw new CustomException(ErrorCode.ORDER_UNABLE_MENU);
		}

		if (option != null && (option.isSoldOut() || option.isHidden())) {
			throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
		}

		Cart cart =
				Cart.builder()
						.user(user)
						.store(store)
						.menu(menu)
						.menuOption(option)
						.quantity(req.quantity())
						.cartMode(req.cartMode())
						.build();

		cartRepository.save(cart);

		return new SaveCartResDTO(cart);
	}

	@Transactional
	public List<CartResDTO> findCartList(User user) {
		List<Cart> cartList = cartRepository.findByUserAndDeletedDateIsNull(user);
		return cartList.stream().map(CartResDTO::new).toList();
	}

	@Transactional
	public CartResDTO updateCart(User user, UUID cartId, UpdateCartReqDTO req) {
		Cart cart =
				cartRepository
						.findById(cartId)
						.orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

		if (!cart.getUser().getUserId().equals(user.getUserId())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_CART_ACCESS);
		}

		cart.updateQuantity(req.quantity());

		if (req.menuOptionId() != null) {
			MenuOption option =
					menuOptionRepository
							.findById(req.menuOptionId())
							.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));

			if (option != null && (option.isSoldOut() || option.isHidden())) {
				throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
			}

			cart.updateMenuOption(option);
		}

		return new CartResDTO(cart);
	}

	@Transactional
	public void deleteCart(User user, UUID cartId) {
		Cart cart =
				cartRepository
						.findById(cartId)
						.orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

		if (!cart.getUser().getUserId().equals(user.getUserId())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_CART_ACCESS);
		}

		cart.delete(user);
	}
}
