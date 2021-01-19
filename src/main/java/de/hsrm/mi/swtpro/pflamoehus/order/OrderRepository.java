package de.hsrm.mi.swtpro.pflamoehus.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Order-Repository for different operations to apply on the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    /**
     * Find a order by its id.
     * 
     * @param orderNR wanted order
     * @return order
     */
    Optional<Order> findByOrderNR(long orderNR);

    /**
     * Find a order by its user.
     * 
     * @param user user who placed the order
     * @return all orders placed by this user
     */
    List<Order> findByUser(User user);

    /**
     * @param email customer's eail
     * @return all orders placed by the customer with this email
     */
    List<Order> findByCustomerEmail(String email);

    /**
     * Sort all orders by their delivery date.
     * 
     * @return list of orders sorted by deliverydate 
     */
    List<Order> findAllByOrderByDeliveryDateAsc();

    /**
     * @param date Date of the delivery
     * @return all orders due to that date
     */
    List<Order> findByDeliveryDate(LocalDate date);


}
