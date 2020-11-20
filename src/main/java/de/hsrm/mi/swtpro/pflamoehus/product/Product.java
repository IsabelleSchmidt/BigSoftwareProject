package de.hsrm.mi.swtpro.pflamoehus.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @NotEmpty  
    private int itemNumber;

    @NotEmpty
    @Size(max = 90)
    private String name;

    @NotEmpty
    @Size(max = 90)
    private String productType;

    private String roomType;

    @NotEmpty
    private Double price;

    @NotEmpty
    private String picture;

    
    private String tags;

    @NotEmpty
    private Boolean availability;

    private Integer size;
    
}
