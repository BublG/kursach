package com.art.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_topic")
public class Topic {

    @Id
    private long id;

    private String name;

    public Topic() {}

    public Topic(String name) {
        this.name = name;
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
}
