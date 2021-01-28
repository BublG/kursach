package com.art.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "t_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_id")
    private ItemCollection itemCollection;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;

    public Item(){}

    public Item(@Size(min = 1) String name, ItemCollection itemCollection, Set<Tag> tags) {
        this.name = name;
        this.itemCollection = itemCollection;
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCollection getCollection() {
        return itemCollection;
    }

    public void setCollection(ItemCollection itemCollection) {
        this.itemCollection = itemCollection;
    }

}
