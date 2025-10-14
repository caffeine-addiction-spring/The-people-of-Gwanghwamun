package com.caffeine.gwanghwamun.domain.order.order_status_log.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.order.order_status_log.dto.OrderStatusLogResDTO;
import com.caffeine.gwanghwamun.domain.order.order_status_log.entity.OrderStatusLog;
import com.caffeine.gwanghwamun.domain.order.order_status_log.repository.OrderStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusLogService {

  private final OrderStatusLogRepository orderStatusLogRepository;

  private final OrderStatusLogRepository logRepository;

  public Page<OrderStatusLogResDTO> findLogList(Pageable pageable) {
    return logRepository.findAll(pageable)
        .map(OrderStatusLogResDTO::new);
  }

  public Page<OrderStatusLogResDTO> findLogByUser(Long userId, Pageable pageable) {
    return logRepository.findAllByUser_UserId(userId, pageable)
        .map(OrderStatusLogResDTO::new);
  }

  public Page<OrderStatusLogResDTO> findLogByStore(UUID storeId, Pageable pageable) {
    return logRepository.findAllByStore_StoreId(storeId, pageable)
        .map(OrderStatusLogResDTO::new);
  }

  public Page<OrderStatusLogResDTO> findLogByOrder(UUID orderId, Pageable pageable) {
    return logRepository.findAllByOrder_OrderId(orderId, pageable)
        .map(OrderStatusLogResDTO::new);
  }

  public OrderStatusLogResDTO findLogById(UUID logId) {
    OrderStatusLog log = logRepository.findById(logId)
        .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    return new OrderStatusLogResDTO(log);
  }
}
