package com.art.repository;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteAllByItemCollection(ItemCollection itemCollection);
    Item findItemById(Long id);
    Item findItemByName(String name);
}
