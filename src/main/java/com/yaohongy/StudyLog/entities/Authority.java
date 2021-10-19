package com.yaohongy.StudyLog.entities;

import java.util.Collection;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Authority")
public class Authority {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authority_id")
    private long id;

    @Column(name = "authority", unique = true)
    private String authority;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "authorities")
    private Collection<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setRole(String authority) {
        this.authority = authority;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }    
}
