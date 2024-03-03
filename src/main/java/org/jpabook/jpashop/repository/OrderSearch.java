package org.jpabook.jpashop.repository;

import lombok.Getter;
import lombok.Setter;
import org.jpabook.jpashop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;
}
