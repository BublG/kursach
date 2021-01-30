package com.art.service;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import com.art.entity.Tag;
import com.art.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    public Set<String> getTagsNames(Item item) {
        Set<String> names = new HashSet<>();
        for (Tag tag : item.getTags())
            names.add(tag.getName());
        return names;
    }

    public Item findItemByName(String name) {
        return itemRepository.findItemByName(name);
    }
}
