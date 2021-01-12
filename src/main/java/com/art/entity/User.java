package com.art.entity;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, message = "Enter username")
    private String username;

    @Size(min = 8, message = "Password is at least 8 characters")
    private String password;

    @Size(min = 1, message = "Enter email")
    private String email;

    private int status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ItemCollection> itemCollections;

    public User() {
    }

    public Set<ItemCollection> getCollections() {
        return itemCollections;
    }

    public void setCollections(Set<ItemCollection> itemCollections) {
        this.itemCollections = itemCollections;
    }

    @Override
    public java.util.Collection getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
