package com.art.service;

import com.art.entity.ItemCollection;
import com.art.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

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
}
