package org.jpabook.jpashop.repository.query.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
            List<OrderQueryItemDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderQueryItemDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "SELECT new org.jpabook.jpashop.repository.query.order.OrderQueryItemDto(oi.order.id, i.name, oi.orderPrice, oi.count) FROM OrderItem oi" +
                        " JOIN oi.item i" +
                        " WHERE oi.order.id = :orderId", OrderQueryItemDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "SELECT new org.jpabook.jpashop.repository.query.order.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) FROM Order o" +
                                " JOIN o.member m" +
                                " JOIN o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
