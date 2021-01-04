package com.art.entity;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    private long id;

    public Tag(){}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
