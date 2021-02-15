package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;


/*
 * Product-Entitiy for its database.
 * One Object = one group of products.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 6
 */
@Entity
@Table(name = "Product")
public class Product {

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articlenr;

    @Version
    @JsonIgnore
    private long version;

    @NotNull
    @Size(max = 90)
    @Column(unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "producttype")
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room")
    private RoomType roomType;

    @NotNull(message = "Der Preis darf nicht leer bleiben.")
    @Positive(message = "Der Preis muss positiv sein.")
    @Digits(integer = 5, fraction = 2, message="Der Preis darf max. 5 Stellen vor und 2 Stellen nach dem Komma besitzen.")
    private double price = 0.0;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Picture> allPictures = new HashSet<>();

    @PositiveOrZero(message = "Zahl muss positiv oder Null sein.")
    @Digits(integer = 3, fraction = 2, message = "Zahl darf max. 3 Stellen vor und 2 Stellen nach dem Komma gross sein.")
    private double height = 0.0;

    @PositiveOrZero(message = "Zahl muss positiv oder Null sein.")
    @Digits(integer = 3, fraction = 2, message = "Zahl darf max. 3 Stellen vor und 2 Stellen nach dem Komma gross sein.")
    private double width = 0.0;

    @PositiveOrZero(message = "Zahl muss positiv oder Null sein.")
    @Digits(integer = 3, fraction = 2, message = "Zahl darf max. 3 Stellen vor und 2 Stellen nach dem Komma gross sein.")
    private double depth = 0.0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "Product_Tags", joinColumns = @JoinColumn(name = "articlenr"), inverseJoinColumns = @JoinColumn(name = "tagID"))
    private Set<Tag> allTags = new HashSet<>();

    @Column(name = "available")
    @PositiveOrZero
    private int available = 0;

    @NotNull
    @Size(min = 10, max = 180)
    private String description;

    @NotNull
    @Size(min = 10, max = 180)
    private String information;


    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH)
    @JsonIgnore
    private Set<OrderDetails> allOrderDetails = new HashSet<>();
    
    /**
     * Get information.
     * 
     * @return information
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * Set information.
     * 
     * @param information information that has to be set
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Get description.
     * 
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set description.
     * 
     * @param description description that has to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * 
     * @param name name that has to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get producttype.
     * 
     * @return producttype
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Set producttype.
     * 
     * @param productType producttype that has to be set
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    /**
     * Get roomtype.
     * 
     * @return roomtype
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Set roomtype.
     * 
     * @param roomType roomtype that has to be set
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    /**
     * Get price.
     * 
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Set price.
     * 
     * @param price price that has to be set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Get all pictures.
     * 
     * @return pictures
     */
    public Set<Picture> getAllPictures() {
        return allPictures;
    }

   

    /**
     * Set allPicutres.
     * 
     * @param allPictures pictures that have to be set
     */
    public void setAllPictures(Set<Picture> allPictures) {
        this.allPictures = allPictures;
    }

    /**
     * Get articlenr.
     * 
     * @return articlenr
     */
    public long getArticlenr() {
        return articlenr;
    }

    /**
     * Get height.
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set height.
     * 
     * @param height height that has to be set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Get width.
     * 
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Set width.
     * 
     * @param width width that has to be set
     */
    public void setWidth(double width) {
        this.width = width;

    }

    /**
     * Get depth.
     * 
     * @return depth
     */
    public double getDepth() {
        return depth;
    }

    /**
     * Set depth.
     * 
     * @param depth depth that has to be set
     */
    public void setDepth(double depth) {
        this.depth = depth;
    }

    /**
     * Get all tags.
     * 
     * @return all tags
     */
    public Set<Tag> getAllTags() {
        return allTags;
    }

    /**
     * Set all tags.
     * 
     * @param allTags tags that have to be set
     */
    public void setAllTags(Set<Tag> allTags) {
        this.allTags = allTags;
    }

  
    /**
     * Get number of available items.
     * 
     * @return available items
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Set number of available items.
     * 
     * @param available number of available items that has to be set
     */
    public void setAvailable(int available) {
        this.available = available;
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }

   
  
    
    /** 
     * Get all order details.
     * 
     * @return all order details
     */
    public Set<OrderDetails> getAllOrderDetails() {
        return allOrderDetails;
    }

    
    /** 
     * Set all order details.
     * 
     * @param allOrderDetails all order details
     */
    public void setAllOrderDetails(Set<OrderDetails> allOrderDetails) {
        this.allOrderDetails = allOrderDetails;
    }

    /**
     * To generate a product as a string.
     * 
     * @return string
     */
    @Override
    public String toString() {
        return "{allPictures=" + allPictures + ", allTags=" + allTags + ", articlenr=" + articlenr
                + ", available=" + available + ", depth=" + depth + ", description=" + description + ", height="
                + height + ", information=" + information + ", name=" + name + ", price=" + price + ", productType="
                + productType + ", roomType=" + roomType + ", version=" + version + ", width=" + width + "}";
    }
   


  


}
