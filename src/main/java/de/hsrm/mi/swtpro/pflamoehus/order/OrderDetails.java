package de.hsrm.mi.swtpro.pflamoehus.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.springframework.validation.annotation.Validated;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

@Entity
@Validated
public class OrderDetails {
    
    @Id
    @GeneratedValue
    private long orderDetailsID;

    @Version
    private long version;

    @OneToOne(mappedBy = "orderNR")
    @JoinColumn(name = "OrderID")
    private Order orderID;

    @ManyToOne
    private Status statusID;

    @ManyToOne
    private Product articleNR;

    @Override
    public String toString() {
        return "OrderDetails [articleNR=" + articleNR + ", orderDetailsID=" + orderDetailsID + ", orderID=" + orderID
                + ", statusID=" + statusID + ", version=" + version + "]";
    }

    public long getOrderDetailsID() {
        return orderDetailsID;
    }


    public long getVersion() {
        return version;
    }


    public Order getOrderID() {
        return orderID;
    }

    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    public Product getArticleNR() {
        return articleNR;
    }

    public void setArticleNR(Product articleNR) {
        this.articleNR = articleNR;
    }




    
}
