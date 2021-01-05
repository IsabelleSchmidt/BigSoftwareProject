package de.hsrm.mi.swtpro.pflamoehus.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.validation.annotation.Validated;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/*
 * OrderDetails-Entity for its database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Entity
@Validated
public class OrderDetails {
    
    @Id
    @GeneratedValue
    private long orderDetailsID;

    @Version
    private long version;

    @ManyToOne
    @JsonIgnore
    private Order orderID;

    @ManyToOne
    private Status statusID;

    @ManyToOne
    private Product articleNR;

    
    /** 
     * Orderdetails to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "OrderDetails [articleNR=" + articleNR + ", orderDetailsID=" + orderDetailsID + ", orderID=" + orderID
                + ", statusID=" + statusID + ", version=" + version + "]";
    }

    
    /** 
     * Get orderdetails-id.
     * 
     * @return long
     */
    public long getOrderDetailsID() {
        return orderDetailsID;
    }


    
    /** 
     * Get version.
     * 
     * @return long
     */
    public long getVersion() {
        return version;
    }


    
    /** 
     * Get orderid.
     * 
     * @return Order
     */
    public Order getOrderID() {
        return orderID;
    }

    
    /** 
     * Set orderid.
     * 
     * @param orderID to be set
     */
    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }

    
    /** 
     * Get statusid.
     * 
     * @return Status
     */
    public Status getStatusID() {
        return statusID;
    }

    
    /** 
     * Set statusid.
     * 
     * @param statusID to be set
     */
    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    
    /** 
     * Get articlenr.
     * 
     * @return Product
     */
    public Product getArticleNR() {
        return articleNR;
    }

    
    /** 
     * Set articlenr.
     * 
     * @param articleNR to be set
     */
    public void setArticleNR(Product articleNR) {
        this.articleNR = articleNR;
    }




    
}
