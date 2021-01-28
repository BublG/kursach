package com.art.repository;

import com.art.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findTagByName(String name);
}
