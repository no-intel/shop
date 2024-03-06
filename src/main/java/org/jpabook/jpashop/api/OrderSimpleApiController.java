package org.jpabook.jpashop.api;

import lombok.RequiredArgsConstructor;
import org.jpabook.jpashop.domain.Order;
import org.jpabook.jpashop.repository.OrderRepository;
import org.jpabook.jpashop.repository.OrderSearch;
import org.jpabook.jpashop.repository.OrderSimpleQueryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * 엔티티가 서로 호출해서 무한루프 order -> member -> order -> ....
     * \@JsonIgnore로 끊어줘야함
     *
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        return orderRepository.findAllByCriteria(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> orderV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        List<Order> orders = orderRepository.findAllMemberDelivery();
        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }

}
