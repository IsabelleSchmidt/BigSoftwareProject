package de.hsrm.mi.swtpro.pflamoehus.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/*
 * OrderDetails-Repository for different operations to apply on the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    
    /**
     * Find a orderdetail by its id.
     * 
     * @param id wanted orderdetail
     * @return orderdetails
     */
    Optional<OrderDetails> findByOrderDetailsID(long id);

    /**
     * Find orderdetails by its order-id.
     * 
     * @param id wanted order
     * @return orderdetails
     */
    List<OrderDetails> findByOrderID(Order order);

    /**
     * Find a orderdetail by its order-id and group it after the different status codes.
     * 
     * @param id wanted order
     * @return orderdetails
     */
    List<OrderDetails> findByOrderIDGroupByStatusID(Order id);

    /**
     * Find orderdetails by a certain product id.
     * 
     * @param id wanted orderdetails
     * @return orderdetail
     */
    List<OrderDetails> findByProductID(Product product);
}
