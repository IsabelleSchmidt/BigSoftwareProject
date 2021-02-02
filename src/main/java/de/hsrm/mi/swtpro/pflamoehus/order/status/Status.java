package de.hsrm.mi.swtpro.pflamoehus.order.status;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;


/*
 * Status-Entity for its database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Entity
@Validated
public class Status {

    @Id
    @GeneratedValue
    private long statusID;

    @Version
    @JsonIgnore
    private long version = 1;
 
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Statuscode statuscode;

    @OneToMany(mappedBy = "statusID", cascade = CascadeType.DETACH)
    private Set<Order> allOrders = new HashSet<>();

    @OneToMany(mappedBy = "statusID", cascade = CascadeType.DETACH)
    private Set<OrderDetails> allOrdersDetails = new HashSet<>();

    /**
     * Get statusId.
     * 
     * @return long
     */
    public long getStatusID() {
        return statusID;
    }


    /**
     * Get statuscode.
     * 
     * @return String
     */
    public Statuscode getStatuscode() {
        return statuscode;
    }

    /**
     * Set statuscode.
     * 
     * @param statuscode to be set
     */
    public void setStatuscode(Statuscode statuscode) {
        this.statuscode = statuscode;
    }


    /**
     * Status to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Status [ statusID=" + statusID
                + ", statuscode=" + statuscode + ", version=" + version + "]";
    }

    
    /** 
     * Get all orders.
     * 
     * @return all orders
     */
    public Set<Order> getAllOrders() {
        return allOrders;
    }

    
    /** 
     * Set all orders.
     * 
     * @param allOrders to be set.
     */
    public void setAllOrders(Set<Order> allOrders) {
        this.allOrders = allOrders;
    }

    
    /** 
     * Get all order details.
     * 
     * @return all order details
     */
    public Set<OrderDetails> getAllOrdersDetails() {
        return allOrdersDetails;
    }

    
    /** 
     * Set all order details.
     * 
     * @param allOrdersDetails to be set
     */
    public void setAllOrdersDetails(Set<OrderDetails> allOrdersDetails) {
        this.allOrdersDetails = allOrdersDetails;
    }

}
