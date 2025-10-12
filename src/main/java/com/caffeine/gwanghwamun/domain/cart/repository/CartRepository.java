package com.caffeine.gwanghwamun.domain.cart.repository;

import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
