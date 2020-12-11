package de.hsrm.mi.swtpro.pflamoehus.tags;

import java.util.HashSet;
import java.util.Set;

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

@Entity
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    private long version;

    @ManyToMany(mappedBy = "allTags")
    @JsonIgnore //one reference of a bi-directional relationship gets ignored, so the infinite occursion is solved
    private Set<Product> allProductsWithTag = new HashSet<Product>();

    @Size(min=3)
    @Column(name="value", unique=true)
    private String value;

    @PreRemove 
    private void removeTagsFromProducts(){
       
            for(Product product: allProductsWithTag){
                product.getAllTags().remove(this);
            }
        
    }

    /** 
     * @return String
     */
    public String getValue() {
        return this.value;
    }

    /** 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    /** 
     * @return long
     */
    public long getVersion() {
        return this.version;
    }

    
    /** 
     * @param version
     */
    public void setVersion(long version) {
        this.version = version;
    }

    
    /** 
     * @return 
     * all products with the tags
     */
    public Set<Product> getAllProductsWithTag() {
        return this.allProductsWithTag;
    }

    
    /** 
     * @param allProductsWithTag
     */
    public void setAllProductsWithTag(Set<Product> allProductsWithTag) {
        this.allProductsWithTag = allProductsWithTag;
    }

    @Override
    public String toString() {
        return "Tag [allProductsWithTag=" + allProductsWithTag + ", id=" + id + ", value=" + value + ", version="
                + version + "]";
    }

    public long getId() {
        return id;
    }

   public void addProduct(Product product){

       allProductsWithTag.add(product);
   }

 

    
    
}
