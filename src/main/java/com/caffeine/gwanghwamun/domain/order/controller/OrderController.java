package com.caffeine.gwanghwamun.domain.order.controller;

import com.caffeine.gwanghwamun.domain.order.dto.SaveOrderRequestDTO;
import com.caffeine.gwanghwamun.domain.order.dto.SaveOrderResponseDTO;
import com.caffeine.gwanghwamun.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<SaveOrderResponseDTO> saveOrder(@RequestBody SaveOrderRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(request));
    }

}
