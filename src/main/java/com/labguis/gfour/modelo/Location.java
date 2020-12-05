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
    @Column(unique = true, name = "number_id")
    private String numberId;
    private String name;
    
    @ManyToOne
    private User user;

    public Location() {
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
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
