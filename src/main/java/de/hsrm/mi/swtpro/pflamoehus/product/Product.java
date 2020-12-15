package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.validation.product_db.*;

/**
 * 1 Object = 1 group of products
 */
@Entity
@Table(name = "Product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long articlenr;

    @Version
    @JsonIgnore
    private long version;

    @NotNull
    @Size(max = 90)
    @Column(unique = true)
    private String name;

    @NotNull
    @ValidProductType
    @Column(name="producttype")
    private String productType;

    @ValidRoomType
    @Column(name="room")
    private String roomType;

    @NotNull
    @Positive @Digits(integer = 5, fraction = 2)
    private double price=0.0;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
    private Set<Picture> allPictures = new HashSet<Picture>();

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double height=0.0;

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double width=0.0;

    @PositiveOrZero @Digits(integer=3, fraction=2)
    private double depth=0.0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="Product_Tags", joinColumns = @JoinColumn(name="articlenr"), inverseJoinColumns = @JoinColumn(name="tagID"))
    private Set<Tag> allTags = new HashSet<Tag>();

    @Column(name="available")
    @PositiveOrZero
    private int nrAvailableItems = 0;

    @NotNull
    @Size(min=10, max=180)
    private String description;

    @NotNull
    @Size(min=10, max=180)
    private String information;

    
    /** 
     * @return String
     */
    public String getInformation() {
        return this.information;
    }

    
    /** 
     * @param information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @return String
     */
    //Getter & Setter
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getProductType() {
        return productType;
    }

    
    /** 
     * @param productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    
    /** 
     * @return String
     */
    public String getRoomType() {
        return roomType;
    }

    
    /** 
     * @param roomType
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    
    /** 
     * @return Double
     */
    public Double getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    
    /** 
     * @return String
     */
    public Set<Picture> getAllPictures() {
        return allPictures;
    }

    
    /** 
     * @param picture
     */
    public void addPicture(Picture picture) {
       this.allPictures.add(picture);
    }

    
    /** 
     * @param allPictures
     */
    public void setALlPictures(HashSet<Picture> allPictures){
        this.allPictures = allPictures;
    }
    
    /** 
     * @return long
     */
    public long getArtikelnr() {
        return articlenr;
    }


    
    /** 
     * @return double
     */
    public double getHeight() {
        return height;
    }

    
  
    
    /** 
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }


    
    /** 
     * @return double
     */
    public double getWidth() {
        return width;
    }

    
   
    
    /** 
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;

    }

    
    /** 
     * @return double
     */
    public double getDepth() {
        return depth;
    }

   
    
    /** 
     * @param depth
     */
    public void setDepth(double depth) {
        this.depth = depth;
    }

    
    /** 
     * @return Set<Tag>
     */
    public Set<Tag> getAllTags() {
        return allTags;
    }

    
    /** 
     * @param allTags
     */
    public void setAllTags(HashSet<Tag> allTags) {
        this.allTags = allTags;
    }

    
    /** 
     * @return int
     */
    public int getNrAvailableItems() {
        return nrAvailableItems;
    }

    
    /** 
     * @param nrAvailableItems
     */
    public void setNrAvailableItems(int nrAvailableItems) {
        this.nrAvailableItems = nrAvailableItems;
    }

    
    /** 
     * @param tag
     */
    public void addTag(Tag tag){
        if(!allTags.contains(tag)){
            allTags.add(tag);
        }
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Product [allTags=" + allTags + ", articlenr=" + articlenr + ", depth=" + depth + ", height=" + height
                + ", name=" + name + ", nrAvailableItems=" + nrAvailableItems + ", pictures =" + allPictures.toString() + ", price="
                + price + ", productType=" + productType + ", roomType=" + roomType + ", version=" + version
                + ", width=" + width + ", description=" + description + ", information=" + information + "]";
    }

    
    /** 
     * @return long
     */
    public long getArticlenr() {
        return articlenr;
    }


    
    /** 
     * @return long
     */
    public long getVersion() {
        return version;
    }

   

    
    /** 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    
    /** 
     * @param allTags
     */
    public void setAllTags(Set<Tag> allTags) {
        this.allTags = allTags;
    }
   

    public void deletePicture(Picture picture){
       allPictures.remove(picture);
    }
    
}
