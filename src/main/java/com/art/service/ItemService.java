package com.art.service;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import com.art.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item findItemById(Long id) {
        return itemRepository.findItemById(id);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }
}
