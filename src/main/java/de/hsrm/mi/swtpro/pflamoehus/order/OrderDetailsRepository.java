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
     * @param order wanted order
     * @return orderdetails
     */
    List<OrderDetails> findByOrderID(Order order);

    /**
     * Find orderdetails by a certain product id.
     * 
     * @param product wanted orderdetails
     * @return orderdetail
     */
    List<OrderDetails> findByArticleNR(Product product);
}
