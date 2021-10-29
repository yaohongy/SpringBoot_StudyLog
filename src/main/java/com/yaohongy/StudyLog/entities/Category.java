package com.yaohongy.StudyLog.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Category_id")
    private long id;

    @Column(name = "Category_name")
    @NotBlank(message = "Category name can not be blank")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany
    private Collection<StudyLog> logs;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<StudyLog> getLogs() {
        return logs;
    }

    public void setLogs(Collection<StudyLog> logs) {
        this.logs = logs;
    }



}
