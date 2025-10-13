package com.caffeine.gwanghwamun.domain.order.controller;

import com.caffeine.gwanghwamun.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

	private OrderService orderService;

	//	@PostMapping
	//	public ResponseEntity<SaveOrderResDTO> saveOrder(@RequestBody SaveOrderReqDTO request) {
	//		return ResponseEntity.status(HttpStatus.CREATED)
	//				.body(orderService.saveOrder(request, UUID.randomUUID()));
	//	}
}
