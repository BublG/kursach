package com.art.service;

import com.art.entity.Tag;
import com.art.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Set<String> allTagsNames() {
        List<Tag> tags = tagRepository.findAll();
        Set<String> names = new HashSet<>();
        for (Tag tag : tags)
            names.add(tag.getName());
        return names;
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public Tag loadTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

}
