package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ValidProductType
    private String productType;

    @ValidRoomType
    private String roomType;

    @NotEmpty
    @Positive @Digits(integer = 5, fraction = 2)
    private Double price;

    private String picture;

    private ArrayList< @Size(min = 3)String> tags;

    @NotEmpty
    private Boolean availability;

    @NotNull 
    @Size(max=3)
    private ArrayList<@Digits(integer = 3, fraction=2) @Positive Double> productSize;

    //Getter & Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public ArrayList<Double> getProductSize() {
        return productSize;
    }

    
    public void setProductSize(ArrayList<Double> productSize) {
        this.productSize = productSize;
    }

    /**
     * Changes the saved values of the products height, width or depth.
     * If a value should not be changed, the value needs to be set to -1
     * @param height contains either the new value for the product's height or -1
     * @param width contains either the new value for the product's width or -1
     * @param depth contains either the new value for the product's depth or -1
     */
    public void changeProductDimensions(double height, double width, double depth){
        if(height != -1){
            this.productSize.set(0, height);
        }

        if(width != -1){
            this.productSize.set(1,width);
        }

        if(depth != -1){
            this.productSize.set(2, depth);
        }
    }
    
    
}
