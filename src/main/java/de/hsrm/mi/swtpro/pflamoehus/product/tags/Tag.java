package de.hsrm.mi.swtpro.pflamoehus.product.tags;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/** 
 * Tag-entity for its database.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @ManyToMany(mappedBy = "allTags", cascade = CascadeType.DETACH)
    @JsonIgnore // one reference of a bi-directional relationship gets ignored, so the infinite occursion is solved
    private Set<Product> allProductsWithTag = new HashSet<>();

    @Size(min = 3)
    @Column(name = "value", unique = true)
    private String value;

    /**
     * Before removing a product, the associated tags have to get removed.
     */
    @PreRemove
    private void removeTagsFromProducts() {

        for (Product product : allProductsWithTag) {
            product.getAllTags().remove(this);
        }

    }

    /**
     * Get value.
     * 
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set value.
     * 
     * @param value value that has to be set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Set version.
     * 
     * @param version version that has to be set
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Get all products with a certain tag.
     * 
     * @return all products with the tags
     */
    public Set<Product> getAllProductsWithTag() {
        return this.allProductsWithTag;
    }

    /**
     * Set all products with a certain tag.
     * 
     * @param allProductsWithTag all products with certain tags that have to be set
     */
    public void setAllProductsWithTag(Set<Product> allProductsWithTag) {
        this.allProductsWithTag = allProductsWithTag;
    }

    /**
     * For turning a tag into a string.
     * 
     * @return tag as string
     */
    @Override
    public String toString() {
        return "Tag [allProductsWithTag=" + allProductsWithTag + ", id=" + id + ", value=" + value + ", version="
                + version + "]";
    }

    /**
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Adding a product into the tag list.
     * 
     * @param product product which has to be added
     */
    public void addProduct(Product product) {
        if(!allProductsWithTag.contains(product)){
           allProductsWithTag.add(product); 
        }
        
    }

    /**
     * Removes a product from the list.
     * 
     * @param product product whicht has to be removed
     */
    public void removeProduct(Product product){
        if(allProductsWithTag != null){
            allProductsWithTag.add(product); 
        }
    }

}
