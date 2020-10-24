/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.modelo;

import javax.persistence.*;


/**
 *
 * @author Nicolas Castillo
 */
@Entity
@Table(name="type_devices")
public class TypeDevice {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id; 
    @Column(unique = true)
    private String name;
    @Column(nullable = true)
    private String description;
    
    @ManyToOne
    private User user;

    public TypeDevice() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    
}
