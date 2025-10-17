package com.caffeine.gwanghwamun.domain.cart.repository;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.cart.entity.CartItemOption;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemOptionRepository extends JpaRepository<CartItemOption, UUID> {

	List<CartItemOption> findAllByCartAndDeletedDateIsNull(Cart cart);

	void deleteAllByCart(Cart cart);

	List<CartItemOption> findAllByMenuOption_MenuOptionIdIn(List<UUID> uuids);
}
