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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String number_id;
    private String name;
    
    @ManyToOne
    private User user;

    public Location() {
    }

    public String getNumber_id() {
        return number_id;
    }

    public void setNumber_id(String number_id) {
        this.number_id = number_id;
    }
       
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
