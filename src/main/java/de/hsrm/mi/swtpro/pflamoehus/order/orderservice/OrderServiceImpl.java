package de.hsrm.mi.swtpro.pflamoehus.order.orderservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * OrderServiceImpl for implementing the interface 'OrderService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired OrderDetailsService orderDetailsService;

    static final Logger orderServiceLogger = org.slf4j.LoggerFactory.getLogger(OrderServiceImpl.class);

    
    /**
     * Edits and saves the editet order.
     * 
     * @param newOrder that has been edited
     * @return order
     */
    @Override
    @Transactional
    public Order editOrder(Order newOrder) {
        Order savedorder = null;
        try {
            savedorder = orderRepo.save(newOrder);

        } catch (OptimisticLockException ole) {
            orderServiceLogger.error("Order could not be saved.");
            throw new OrderServiceException("Order could not be saved.");

        }

        return savedorder;
    }

    /**
     * Find a order by its id.
     * 
     * @param orderNR id
     * @return order
     */
    @Override
    public Order findOrderByOrderNR(long orderNR) {
        Optional<Order> order = orderRepo.findByOrderNR(orderNR);
        if (order.isEmpty()) {

            throw new OrderServiceException("No order with the given ID was found.");
        }

        return order.get();
    }

    /**
     * Find all orders from a certain user.
     * 
     * @param customer from who the orders are wanted
     * @return list of orders
     */
    @Override
    public List<Order> findAllCustomerOrders(User customer) {
        return orderRepo.findByUser(customer);
    }

    /**
     * Sort all orders by date.
     * 
     * @return list of orders
     */
    @Override
    public List<Order> getAllOrdersSortedByDate() {
        return orderRepo.findAllByOrderByDeliveryDateAsc();
    }

    /**
     * Find all orders with a certain date.
     * 
     * @param date wanted
     * @return list of orders
     */
    @Override
    public List<Order> findAllOrdersOnDate(LocalDate date) {
        return orderRepo.findByDeliveryDate(date);
    }

    /**
     * Find all orders from a certain user with its email adress.
     * 
     * @param email wanted user
     * @return list of orders
     */
    @Override
    public List<Order> findAllCustomerOrdersViaEmail(String email) {
        return orderRepo.findByCustomerEmail(email);
    }

    /**
     * Delete order.
     * 
     * @param orderNR to be deleted
     */
    @Override
    @Transactional
    public void deleteOrder(long orderNR) {
        Order order = findOrderByOrderNR(orderNR);
        for(OrderDetails detail : order.getOrderdetails()){
            orderDetailsService.deleteOrderDetail(detail.getOrderDetailsID());
        }
        orderRepo.deleteById(orderNR);
    }

}
