package de.hsrm.mi.swtpro.pflamoehus.order.orderdetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/*
 * OrderDetails-Entity for its database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Entity(name="orderdetails")
@Validated
public class OrderDetails {

    @Id
    @GeneratedValue
    private long orderDetailsID;

    @Version
    private long version = 1;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderID")
    private Order orderID;

    @ManyToOne
    @Valid
    @JoinColumn(name = "statusID")
    private Status statusID;

    @ManyToOne(cascade = CascadeType.DETACH)
    @Valid
    private Product product;

    @Positive
    private int productAmount;

    /**
     * Orderdetails to string.
     * 
     * @return String
     */
  
    @Override
    public String toString() {
        return "OrderDetails [orderDetailsID=" + orderDetailsID + ", orderID=" + orderID + ", product=" + product
                + ", productAmount=" + productAmount + ", statusID=" + statusID + ", version=" + version + "]";
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
     * Get product.
     * 
     * @return Product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set product.
     * 
     * @param product to be set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get amount of products.
     * 
     * @return int
     */
    public int getProductAmount() {
        return productAmount;
    }

    /**
     * Set amount of products.
     * 
     * @param productAmount to be set
     */
    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }


}
