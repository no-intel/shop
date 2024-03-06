package org.jpabook.jpashop.api;

import lombok.RequiredArgsConstructor;
import org.jpabook.jpashop.domain.Order;
import org.jpabook.jpashop.domain.OrderItem;
import org.jpabook.jpashop.repository.OrderRepository;
import org.jpabook.jpashop.repository.OrderSearch;
import org.jpabook.jpashop.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }
}
