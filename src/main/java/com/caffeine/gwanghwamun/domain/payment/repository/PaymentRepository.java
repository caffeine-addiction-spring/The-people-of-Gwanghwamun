package com.caffeine.gwanghwamun.domain.payment.repository;

import com.caffeine.gwanghwamun.domain.payment.entity.Payment;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

	boolean existsByOrderId(UUID orderId);

	Page<Payment> findByUserId(Long userId, Pageable pageable);

	Page<Payment> findByStoreId(UUID storeId, Pageable pageable);
}
