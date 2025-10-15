package com.caffeine.gwanghwamun.domain.cart.repository;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {
	List<Cart> findByUserAndStoreAndDeletedDateIsNull(User user, Store store);
}
