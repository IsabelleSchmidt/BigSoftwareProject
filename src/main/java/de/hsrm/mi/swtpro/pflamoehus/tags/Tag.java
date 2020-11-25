package de.hsrm.mi.swtpro.pflamoehus.tags;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

@Entity
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    private long version;
    
    @ManyToMany(mappedBy = "allTags")
    private Set<Product> allProductsWithTag = new HashSet<Product>();

    @Size(min=3)
    private String value;
}
