package com.caffeine.gwanghwamun.domain.cart.repository;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {}
