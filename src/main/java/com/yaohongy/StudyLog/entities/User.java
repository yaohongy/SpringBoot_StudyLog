package com.yaohongy.StudyLog.entities;

import org.hibernate.validator.constraints.Length;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    @Length(min = 5, message = "Username must have at least 5 characters")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password")
    @Length(min = 8, message = "Password must have at least 8 characters")
    @NotBlank(message = "Password is mandatory")
    private String password; 

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email is not valid")
    private String email;

    @Column(name = "active", nullable = false)
    private int active;

    @JsonIgnore
    @OneToMany
    private Collection<StudyLog> logs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Collection<Authority> authorities;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Collection<StudyLog> getLogs() {
        return logs;
    }

    public void setPosts(Collection<StudyLog> logs) {
        this.logs = logs;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setRoles(Collection<Authority> authorities) {
        this.authorities = authorities;
    }
}
