package org.jpabook.jpashop.repository.query.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderQueryItemDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    public List<OrderFlatDTO> findAllByDto_flat() {
        return em.createQuery(
                "SELECT new org.jpabook.jpashop.repository.query.order.OrderFlatDTO(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " FROM Order o" +
                        " JOIN o.member m" +
                        " JOIN o.delivery d" +
                        " JOIN o.orderItems oi" +
                        " JOIN oi.item i", OrderFlatDTO.class)
                .getResultList();
    }

    private Map<Long, List<OrderQueryItemDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderQueryItemDto> orderItems = em.createQuery(
                        "SELECT new org.jpabook.jpashop.repository.query.order.OrderQueryItemDto(oi.order.id, i.name, oi.orderPrice, oi.count) FROM OrderItem oi" +
                                " JOIN oi.item i" +
                                " WHERE oi.order.id in :orderIds", OrderQueryItemDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderQueryItemDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderQueryItemDto::getOrderId));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());
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
