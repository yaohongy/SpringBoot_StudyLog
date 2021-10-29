package com.yaohongy.StudyLog.entities;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "study_log")
public class StudyLog implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private long id;

    @Column(name = "title", nullable = false)
    @Length(min = 5, message = "Title must have at least 5 characters")
    @NotBlank(message = "Title is mandatory")
    private String title;
    
    @Column(name = "body")
    @NotBlank(message = "Log body is mandatory")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false,updatable = false)
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
