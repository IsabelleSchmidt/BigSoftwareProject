package de.hsrm.mi.swtpro.pflamoehus.product.picture;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;


/**
 * Picture entitiy for its database.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@Entity
@Table(name = "Picture")
public class Picture {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Version
    @JsonIgnore
    private long version;

    /**
     * one reference of a bi-directional relationship gets ignored, so the infinite
     * recursion is solved
     */
    @ManyToOne
    @JoinColumn(name = "product")
    @JsonIgnore
    private Product product;

    @NotEmpty
    @Column(unique = true)
    // @ValidPicture
    private String path;


    /**
     * Get product.
     * 
     * @return product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set product.
     * 
     * @param product product that has to be set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get path.
     * 
     * @return path
     */
    // @Transient
    public String getPath() {
        // if(product == null) return null;
        return this.path;
    }

    /**
     * Set path.
     * 
     * @param path path that has to get set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return this.id;
    }

    /**
     * To generate a picutre as a string.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Picture [id=" + id + ", path=" + path + ", version=" + version + "]";
    }

    /**
     * For deleting all pictures before deleting a product.
     */
    @PreRemove
    public void delete() {
        if (product != null) {
            product.getAllPictures().remove(this);
        }

    }
}
