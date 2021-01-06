package de.hsrm.mi.swtpro.pflamoehus.order.orderservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

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

    @Autowired
    UserService userService;

    static final Logger orderServiceLogger = org.slf4j.LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * For editing a new order.
     * 
     * @param editOrder edited
     * @return order
     */
    @Override
    public Order editOrder(Order editOrder) {
        try {
            editOrder = orderRepo.save(editOrder);
        } catch (OptimisticLockException ole) {
            throw new OrderServiceException("Failed to save edited order");
        }

        return editOrder;
    }

    /**
     * @param newOrder the new order that should be saved into the database saves
     *                 the new order into the order table and adds the new order
     *                 into the corresponding user's order list
     * @return the order that's saved in the OrderRepository
     */
    @Override
    public Order saveNewOrder(Order newOrder) throws OrderServiceException, UserServiceException {
        Order savedorder = null;
        User user;
        try {
            savedorder = orderRepo.save(newOrder);
            if (savedorder.getUser() == null) {
                user = userService.searchUserWithEmail(savedorder.getCustomerEmail());
                savedorder.setUser(user);
            } else {
                user = savedorder.getUser();
            }
            user.getOrders().add(savedorder);

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
        return order.isPresent() ? order.get() : null;
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

}
