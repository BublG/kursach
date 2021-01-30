package com.art.service;

import com.art.entity.ItemCollection;
import com.art.repository.CollectionRepository;
import com.art.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    void setCollectionRepository(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public boolean saveCollection(ItemCollection itemCollection) {
        ItemCollection fromDB = collectionRepository.findItemCollectionById(itemCollection.getId());
        if (fromDB != null) {
            return false;
        }
        collectionRepository.save(itemCollection);
        return true;
    }

    public ItemCollection findById(Long id) {
        ItemCollection collection = collectionRepository.findItemCollectionById(id);
        if (collection == null) {
            throw new NotFoundException("Collection not found");
        }
        return collection;
    }

    @Transactional
    public void deleteCollection(ItemCollection collection) {
        itemRepository.deleteAllByItemCollection(collection);
        collectionRepository.delete(collection);
    }

    public List<ItemCollection> findAll() {
        return collectionRepository.findAll();
    }
}
