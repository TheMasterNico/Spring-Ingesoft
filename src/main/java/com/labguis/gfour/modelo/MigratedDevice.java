package com.labguis.gfour.modelo;

import java.sql.Date;
import javax.persistence.*;

/**
 *
 * @author Nicolas Castillo
 */
@Entity
@Table(name="migrated_devices")
public class MigratedDevice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;    
    @ManyToOne
    private TypeDevice type_device;
    @ManyToOne
    private Location location;
    @ManyToOne
    private User user;
    @ManyToOne
    private User update_user;
    @ManyToOne
    private User owner_user;
    @ManyToOne
    private Agencie agencie;
    
    @Column(nullable = false, unique = true)
    private String standar_key;
    @Column(nullable = false, unique = true)
    private String inv_plate;
    private String switch_ip;
    private String new_ip;
    private String old_ip;
    private int class_room;
    
    private Date register_time;    
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String MAC;
    @Column(nullable = true)
    private String port;

    public MigratedDevice() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeDevice getType_device() {
        return type_device;
    }

    public void setType_device(TypeDevice type_device) {
        this.type_device = type_device;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(User update_user) {
        this.update_user = update_user;
    }

    public User getOwner_user() {
        return owner_user;
    }

    public void setOwner_user(User owner_user) {
        this.owner_user = owner_user;
    }

    public Agencie getAgencie() {
        return agencie;
    }

    public void setAgencie(Agencie agencie) {
        this.agencie = agencie;
    }

    public String getStandar_key() {
        return standar_key;
    }

    public void setStandar_key(String standar_key) {
        this.standar_key = standar_key;
    }

    public String getInv_plate() {
        return inv_plate;
    }

    public void setInv_plate(String inv_plate) {
        this.inv_plate = inv_plate;
    }

    public String getSwitch_ip() {
        return switch_ip;
    }

    public void setSwitch_ip(String switch_ip) {
        this.switch_ip = switch_ip;
    }

    public String getNew_ip() {
        return new_ip;
    }

    public void setNew_ip(String new_ip) {
        this.new_ip = new_ip;
    }

    public String getOld_ip() {
        return old_ip;
    }

    public void setOld_ip(String old_ip) {
        this.old_ip = old_ip;
    }

    public int getClass_room() {
        return class_room;
    }

    public void setClass_room(int class_room) {
        this.class_room = class_room;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    
}
