package de.hsrm.mi.swtpro.pflamoehus.order;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.validation.annotation.Validated;

import de.hsrm.mi.swtpro.pflamoehus.validation.order_db.ValidStatus;

@Entity
@Validated
public class Status {

    @Id
    @GeneratedValue
    private long statusID;

    @Version @JsonIgnore
    private long version;

    @ManyToOne 
    @JsonIgnore
    private HashSet<Order> allOrderNR = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private HashSet<OrderDetails> allOrderDetailNR = new HashSet<>();

    @ValidStatus
    private String statuscode;

 

    public long getStatusID() {
        return statusID;
    }

    public boolean addOrder(Order newOrder){
        return allOrderNR.add(newOrder);
    }

    public boolean removeOrder(Order delOrder){
        return allOrderNR.remove(delOrder);
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

   

    public HashSet<Order> getAllOrderNR() {
        return allOrderNR;
    }

    public void setAllOrderNR(HashSet<Order> allOrderNR) {
        this.allOrderNR = allOrderNR;
    }

    public HashSet<OrderDetails> getAllOrderDetailNR() {
        return allOrderDetailNR;
    }

    public void setAllOrderDetailNR(HashSet<OrderDetails> allOrderDetailNR) {
        this.allOrderDetailNR = allOrderDetailNR;
    }

    public boolean addOrderDetail(OrderDetails details){
        return allOrderDetailNR.add(details);
    }

    public boolean removeOrderDetail(OrderDetails details){
        return allOrderDetailNR.remove(details);
    }

  
 @Override
    public String toString() {
        return "Status [allOrderDetailNR=" + allOrderDetailNR + ", allOrderNR=" + allOrderNR + ", statusID=" + statusID
                + ", statuscode=" + statuscode + ", version=" + version + "]";
    }

//TODO: in allen Entities checken ob wir en Set oder eine Liste brauchen
//TODO: in allen Entities add und remove methoden die boolean zurueckgeben
}
