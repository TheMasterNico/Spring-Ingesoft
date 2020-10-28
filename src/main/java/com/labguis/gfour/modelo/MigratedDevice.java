package com.labguis.gfour.modelo;

import java.time.LocalDateTime;
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
    private TypeDevice typeDevice;
    @ManyToOne
    private Location location;
    @ManyToOne
    private User user;
    @ManyToOne
    private User updateUser;
    @ManyToOne
    private User ownerUser;
    @ManyToOne
    private Agencie agencie;
    
    @Column(nullable = false, unique = true)
    private String standarKey;
    @Column(nullable = false, unique = true)
    private String invPlate;
    private String switchIP;
    private String newIP;
    private String oldIP;
    private int classRoom;
    
    private LocalDateTime registerTime;    
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

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
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

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Agencie getAgencie() {
        return agencie;
    }

    public void setAgencie(Agencie agencie) {
        this.agencie = agencie;
    }

    public String getStandarKey() {
        return standarKey;
    }

    public void setStandarKey(String standarKey) {
        this.standarKey = standarKey;
    }

    public String getInvPlate() {
        return invPlate;
    }

    public void setInvPlate(String invPlate) {
        this.invPlate = invPlate;
    }

    public String getSwitchIP() {
        return switchIP;
    }

    public void setSwitchIP(String switchIP) {
        this.switchIP = switchIP;
    }

    public String getNewIP() {
        return newIP;
    }

    public void setNewIP(String newIP) {
        this.newIP = newIP;
    }

    public String getOldIP() {
        return oldIP;
    }

    public void setOldIP(String oldIP) {
        this.oldIP = oldIP;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
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
