package com.labguis.gfour.modelo;

import javax.persistence.*;

/**
 *
 * @author Nicolas Castillo
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @Column(columnDefinition = "boolean default false")
    private Boolean responsible;

    public User() {
    }

    public Boolean getResponsible() {
        return responsible;
    }

    public void setResponsible(Boolean responsible) {
        this.responsible = responsible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
