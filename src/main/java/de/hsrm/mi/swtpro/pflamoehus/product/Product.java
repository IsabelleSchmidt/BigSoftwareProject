package de.hsrm.mi.swtpro.pflamoehus.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {
    
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;
}
