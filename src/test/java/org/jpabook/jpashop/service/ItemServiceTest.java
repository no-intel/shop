package org.jpabook.jpashop.service;

import org.jpabook.jpashop.domain.item.Item;
import org.jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템_추가() throws Exception {
        //given
        Item item = new Item();
        item.setName("ItemA");
        item.setPrice(100);
        item.setStockQuantity(10);

        //when
        itemRepository.save(item);

        //then
        assertEquals(item, itemRepository.findOne(item.getId()));
    }
    
}