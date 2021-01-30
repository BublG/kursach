package com.art.repository;

import com.art.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<ItemCollection, Long> {
    ItemCollection findItemCollectionById(Long id);
    ItemCollection findItemCollectionByName(String name);
}
