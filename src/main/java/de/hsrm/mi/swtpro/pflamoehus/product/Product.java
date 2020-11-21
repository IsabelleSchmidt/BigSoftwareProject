package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.validation.ValidPrice;
import de.hsrm.mi.swtpro.pflamoehus.validation.ValidProductType;
import de.hsrm.mi.swtpro.pflamoehus.validation.ValidRoomType;

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
    @Size(max = 90)
    private String name;

    @NotEmpty
    @Size(max = 90)
    @ValidProductType
    private String productType;

    @ValidRoomType
    private String roomType;

    @NotEmpty
    @ValidPrice
    private Double price;

    @NotEmpty
    private String picture;

    @Size(min = 3)
    private ArrayList<String> tags;

    @NotEmpty
    private Boolean availability;

    @NotNull
    private ArrayList<Integer> size;
    
}
