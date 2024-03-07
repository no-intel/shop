package org.jpabook.jpashop.repository.query.order;

import lombok.Data;
import org.jpabook.jpashop.domain.OrderItem;

@Data
public class OrderQueryItemDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderQueryItemDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
