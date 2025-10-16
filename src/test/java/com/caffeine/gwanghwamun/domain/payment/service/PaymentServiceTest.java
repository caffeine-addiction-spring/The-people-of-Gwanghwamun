package com.caffeine.gwanghwamun.domain.payment.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentCreateReqDTO;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentResDTO;
import com.caffeine.gwanghwamun.domain.payment.entity.Payment;
import com.caffeine.gwanghwamun.domain.payment.entity.PaymentStatus;
import com.caffeine.gwanghwamun.domain.payment.repository.PaymentRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("결제 생성 성공")
    void createPayment_Success() {
        // given
        UUID orderId = UUID.randomUUID();
        UUID storeId = UUID.randomUUID();
        Long userId = 1L;
        Integer amount = 10000;

        User user = User.builder().userId(userId).build();
        Store store = Store.builder().storeId(storeId).build();
        Order order = Order.builder()
                .orderId(orderId)
                .user(user)
                .store(store)
                .totalPrice(amount)
                .build();

        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .userId(userId)
                .orderId(orderId)
                .storeId(storeId)
                .amount(amount)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        PaymentCreateReqDTO reqDTO = new PaymentCreateReqDTO(amount);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        given(paymentRepository.existsByOrderId(orderId)).willReturn(false);
        given(paymentRepository.save(any(Payment.class))).willReturn(payment);

        // when
        PaymentResDTO result = paymentService.createPayment(orderId, reqDTO, userId);

        // then
        assertThat(result.paymentId()).isEqualTo(payment.getPaymentId());
        assertThat(result.amount()).isEqualTo(amount);
        assertThat(result.paymentStatus()).isEqualTo(PaymentStatus.APPROVED);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("주문이 존재하지 않으면 예외 발생")
    void createPayment_OrderNotFound() {
        // given
        UUID orderId = UUID.randomUUID();
        Long userId = 1L;
        PaymentCreateReqDTO reqDTO = new PaymentCreateReqDTO(10000);

        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(orderId, reqDTO, userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ORDER_NOT_FOUND);
    }

    @Test
    @DisplayName("본인 주문이 아니면 예외 발생")
    void createPayment_Unauthorized() {
        // given
        UUID orderId = UUID.randomUUID();
        Long userId = 1L;
        Long otherUserId = 2L;

        User user = User.builder().userId(otherUserId).build();
        Order order = Order.builder()
                .orderId(orderId)
                .user(user)
                .totalPrice(10000L)
                .build();

        PaymentCreateReqDTO reqDTO = new PaymentCreateReqDTO(10000);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(orderId, reqDTO, userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_UNAUTHORIZED);
    }

    @Test
    @DisplayName("이미 결제가 존재하면 예외 발생")
    void createPayment_AlreadyExist() {
        // given
        UUID orderId = UUID.randomUUID();
        Long userId = 1L;

        User user = User.builder().userId(userId).build();
        Order order = Order.builder()
                .orderId(orderId)
                .user(user)
                .totalPrice(10000L)
                .build();

        PaymentCreateReqDTO reqDTO = new PaymentCreateReqDTO(10000);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        given(paymentRepository.existsByOrderId(orderId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(orderId, reqDTO, userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_ALREADY_EXIST);
    }

    @Test
    @DisplayName("결제 금액이 주문 금액과 다르면 예외 발생")
    void createPayment_InvalidAmount() {
        // given
        UUID orderId = UUID.randomUUID();
        Long userId = 1L;

        User user = User.builder().userId(userId).build();
        Order order = Order.builder()
                .orderId(orderId)
                .user(user)
                .totalPrice(10000L)
                .build();

        PaymentCreateReqDTO reqDTO = new PaymentCreateReqDTO(5000); // 다른 금액

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        given(paymentRepository.existsByOrderId(orderId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(orderId, reqDTO, userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_INVALID_AMOUNT);
    }
}