package com.caffeine.gwanghwamun.domain.cart.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.cart.dto.*;
import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.cart.entity.CartItemOption;
import com.caffeine.gwanghwamun.domain.cart.repository.CartItemOptionRepository;
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
	private final CartItemOptionRepository cartItemOptionRepository;

	@Transactional
	public SaveCartResDTO saveCart(Long userId, SaveCartReqDTO req) {

		User user =
				userRepository
						.findById(userId)
						.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		Store store =
				storeRepository
						.findActiveById(req.storeId())
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(req.menuId())
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

		if (menu.isSoldOut() || menu.isHidden()) {
			throw new CustomException(ErrorCode.ORDER_UNABLE_MENU);
		}

		List<MenuOption> menuOptions =
				menuOptionRepository.findAllByMenuOptionIdInAndMenuId(
						req.menuOptionIdList(), menu.getMenuId());

		for (MenuOption option : menuOptions) {
			if (option.isSoldOut() || option.isHidden()) {
				throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
			}
		}

		int optionPrice = menuOptions.stream().mapToInt(MenuOption::getPrice).sum();

		int itemTotalPrice = (menu.getPrice() + optionPrice) * req.quantity();

		Cart cart =
				Cart.builder()
						.user(user)
						.store(store)
						.menu(menu)
						.quantity(req.quantity())
						.cartMode(req.cartMode())
						.totalPrice(itemTotalPrice)
						.build();

		cartRepository.save(cart);

		List<CartItemOption> cartItemOptions =
				menuOptions.stream()
						.map(option -> CartItemOption.builder().menuOption(option).cart(cart).build())
						.toList();

		cartItemOptionRepository.saveAll(cartItemOptions);

		return new SaveCartResDTO(cart, cartItemOptions);
	}

	@Transactional
	public List<CartResDTO> findCartList(User user) {
		List<Cart> cartList = cartRepository.findByUserAndDeletedDateIsNull(user);
		return cartList.stream().map(CartResDTO::new).toList();
	}

	@Transactional
	public CartUpdateResDTO updateCart(User user, UUID cartId, UpdateCartReqDTO req) {
		Cart cart =
				cartRepository
						.findById(cartId)
						.orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

		if (!cart.getUser().getUserId().equals(user.getUserId())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_CART_ACCESS);
		}

		cart.updateQuantity(req.quantity());

		int optionPrice = 0;

		List<CartItemOption> cartItemOptionList = null;

		if (req.menuOptionIdList() != null && !req.menuOptionIdList().isEmpty()) {
			List<MenuOption> menuOptions =
					menuOptionRepository.findAllByMenuOptionIdInAndMenuId(
							req.menuOptionIdList(), cart.getMenu().getMenuId());

			for (MenuOption option : menuOptions) {
				if (option.isSoldOut() || option.isHidden()) {
					throw new CustomException(ErrorCode.ORDER_UNABLE_MENU_OPTION);
				}
			}

			optionPrice = menuOptions.stream().mapToInt(MenuOption::getPrice).sum();

			cartItemOptionRepository.deleteAllByCart(cart);
			cartItemOptionList =
					menuOptions.stream()
							.map(opt -> CartItemOption.builder().cart(cart).menuOption(opt).build())
							.toList();

			cartItemOptionRepository.saveAll(cartItemOptionList);
		}

		int totalPrice = (cart.getMenu().getPrice() + optionPrice) * cart.getQuantity();

		cart.updateTotalPrice(totalPrice);

		return new CartUpdateResDTO(cart, cartItemOptionList);
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
