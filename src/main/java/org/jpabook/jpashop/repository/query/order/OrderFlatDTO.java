package org.jpabook.jpashop.repository.query.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jpabook.jpashop.domain.Address;
import org.jpabook.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderFlatDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
