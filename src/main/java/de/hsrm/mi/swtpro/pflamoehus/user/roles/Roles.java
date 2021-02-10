package de.hsrm.mi.swtpro.pflamoehus.user.roles;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Roles entitiy.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
@Table(name="roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERoles name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> user = new HashSet<>();

    
    /** 
     * Get name.
     * 
     * @return ERoles
     */
    public ERoles getName() {
        return this.name;
    }

    
    /** 
     * Set name.
     * 
     * @param name name that has to be set
     */
    public void setName(ERoles name) {
        this.name = name;
    }

    
    /** 
     * Get id.
     * 
     * @return Integer
     */
    public Integer getId() {
        return this.id;
    }

    
    /** 
     * Set id.
     * 
     * @param id id that has to be set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    
    /** 
     * Returns the name as a String.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return name.toString();
    }

    /**
     * Get users.
     * 
     * @return list of users
     */
    public Set<User> getUser() {
        return user;
    }

    /**
     * Set users.
     * 
     * @param user users that have to be set
     */
    public void setUser(Set<User> user) {
        this.user = user;
    }

    /**
     * Adds a user to the user list.
     * 
     * @param us user that should get added
     */
    public void addUser (User us){
        if(!user.contains(us)){
            user.add(us);
        }
    }

    /**
     * Removes a user from the user list.
     * 
     * @param us user that should get removed
     */
    public void removeUser(User us){
        if (user != null){
            user.remove(us);
        }
    }

    
    
    
}
