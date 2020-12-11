package de.hsrm.mi.swtpro.pflamoehus.product.picture;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.validation.productDatabase.ValidPicture;

@Entity
@Table(name="Picture")
public class Picture {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @ManyToOne
    @JsonIgnore //one reference of a bi-directional relationship gets ignored, so the infinite occursion is solved
    @JoinColumn(name = "product")
    private Product product;

    @NotEmpty
    @ValidPicture
    private String path; //Pfad zum Bild
    //TODO: speicherart fuer Bilder herausfinden - als Lob byte[]?



    //Getter and Setter

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId(){
        return this.id;
    }
    
    
}
