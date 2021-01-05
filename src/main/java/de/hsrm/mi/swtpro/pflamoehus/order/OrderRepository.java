package de.hsrm.mi.swtpro.pflamoehus.order;

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

    /**
     * Find a order by its id.
     * 
     * @param id wanted order
     * @return order
     */
    Optional<Order> findByOrderNR(long orderNR);

    /**
     * Find a order by its user.
     * 
     * @param user wanted order
     * @return oder
     */
  
    /**
     * Sort all orders by their delivery date.
     * 
     * @return list of orders
     */
    List<Order> findAllByOrderByDeliveryDateAsc();


}
