package de.hsrm.mi.swtpro.pflamoehus.order.orderservice;

import java.time.LocalDate;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * OrderDetailsService for different operations to apply on orderdetails.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public interface OrderService {

    /**
     * @param newOrder the new order that should be saved into the database saves
     *                 the new order into the order table and adds the new order
     *                 into the corresponding user's order list
     * @return the order that's saved in the OrderRepository
     */
    Order editOrder(Order newOrder);

    /**
     * Find a order by its id.
     * 
     * @param orderNR id
     * @return order
     */
    Order findOrderByOrderNR(long orderNR);

    /**
     * Find all orders from a certain user.
     * 
     * @param customer from who the orders are wanted
     * @return list of orders
     */
    List<Order> findAllCustomerOrders(User customer);

    /**
     * Find all orders from a certain user with its email adress.
     * 
     * @param email wanted user
     * @return list of orders
     */
    List<Order> findAllCustomerOrdersViaEmail(String email);

    /**
     * Sort all orders by date.
     * 
     * @return list of orders
     */
    List<Order> getAllOrdersSortedByDate();

    /**
     * Find all orders with a certain date.
     * 
     * @param date wanted
     * @return list of orders
     */
    List<Order> findAllOrdersOnDate(LocalDate date);

    /**
     * Delete order.
     * 
     * @param orderNR to be deleted
     */
    void deleteOrder(long orderNR);

}
