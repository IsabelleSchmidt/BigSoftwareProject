package de.hsrm.mi.swtpro.pflamoehus.order.orderservice;

import java.time.LocalDate;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

public interface OrderService {
    
    Order editOrder(Order newOrder);

    /**
     * @param newOrder the new order that should be saved into the database
     * saves the new order into the order table and adds the new order into the corresponding user's order list
     * @return the order that's saved in the OrderRepository
     */
    Order saveNewOrder(Order newOrder);

    Order findOrderByOrderNR(long OrderNR);

    List<Order> findAllCustomerOrders(User customer);

    List<Order> findAllCustomerOrdersViaEmail(String email);

    List<Order> getAllOrdersSortedByDate();

    List<Order> findAllOrdersOnDate(LocalDate date);


}
