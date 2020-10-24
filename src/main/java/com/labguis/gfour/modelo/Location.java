package com.labguis.gfour.modelo;

import javax.persistence.*;

/**
 *
 * @author Nicolas Castillo
 */
@Entity
@Table(name="locations")
public class Location {
    @Id
    @Column(unique = true)
    private long id;
    private String name;
    
    @ManyToOne
    private User user;

    public Location() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

    
    
}
