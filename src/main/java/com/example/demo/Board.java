package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//primary key 를 가져야한다.
@Entity
@Table(name = "ILDONG_BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // oracle seq와 같은 개념
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 20)
    private String title;
    private String password;
    private boolean deleted;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
