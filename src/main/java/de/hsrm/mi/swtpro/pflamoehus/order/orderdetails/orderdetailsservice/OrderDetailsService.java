package de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/*
 * OrderDetailsService for different operations to apply on orderdetails.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface OrderDetailsService {

    /**
     * For editing a orderDetail.
     * 
     * @param orderDetail to be edited
     * @return OrderDetails
     */
    OrderDetails editOrderDetail(OrderDetails orderDetail);

    /**
     * Finding all OrderDetails in the database.
     * 
     * @return list of order details
     */
    List<OrderDetails> findAll();

    /**
     * Finds a order detail by its id.
     * 
     * @param id to be found
     * @return the order detail
     */
    Optional<OrderDetails> findByID(long id);

    /**
     * Finds order details from a certain order.
     * 
     * @param order with the order details
     * @return list of order details
     */
    List<OrderDetails> findByOrder(Order order);

    /**
     * Finds order details with a certain product in it.
     * 
     * @param product which should be included
     * @return list of order details
     */
    List<OrderDetails> findByProduct(Product product);

    /**
     * Finds order details with a ceratin status.
     * 
     * @param status which should be included
     * @return list of order details
     */
    List<OrderDetails> findByStatus(Status status);

    /**
     * Deletes a certain order detail.
     * 
     * @param id which should get deleted
     */
    void deleteOrderDetail(long id);

}
