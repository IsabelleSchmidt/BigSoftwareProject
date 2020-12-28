package de.hsrm.mi.swtpro.pflamoehus.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERoles name;

    
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

    
    
    
}
