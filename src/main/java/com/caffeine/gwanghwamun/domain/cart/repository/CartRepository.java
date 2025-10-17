package com.caffeine.gwanghwamun.domain.cart.repository;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
  List<Cart> findByUserAndStoreAndDeletedDateIsNull(User user, Store store);

  List<Cart> findByUserAndDeletedDateIsNullOrderByCreateAtDesc(User user);

  Cart findByCartIdAndDeletedDateIsNull(UUID cartId);
}
