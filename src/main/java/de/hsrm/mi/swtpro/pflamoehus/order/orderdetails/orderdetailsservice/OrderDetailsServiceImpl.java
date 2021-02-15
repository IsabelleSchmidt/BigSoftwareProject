package de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice;

import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderDetailsServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/*
 * OrderDetailsServiceImpl for implementing the interface 'OrderDetailsService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    /**
     * For editing a orderDetail.
     * 
     * @param orderDetail to be edited
     * @return OrderDetails
     */
    @Override
    @Transactional
    public OrderDetails editOrderDetail(OrderDetails orderDetail) {

        try {
            orderDetail = orderDetailsRepo.save(orderDetail);
        } catch (OptimisticLockException oLE) {
            LOGGER.error("OrderDetails can only be edited by one person at a time.");
            throw new OrderDetailsServiceException();

        }
        LOGGER.info("ORDER GESPEICHERT.");
        return orderDetail;
    }

    /**
     * Finding all OrderDetails in the database.
     * 
     * @return list of order details
     */
    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepo.findAll();
    }

    /**
     * Finds a order detail by its id.
     * 
     * @param id to be found
     * @return the order detail
     */
    @Override
    public Optional<OrderDetails> findByID(long id) {
        Optional<OrderDetails> orderDetail = orderDetailsRepo.findByOrderDetailsID(id);
        if (orderDetail.isEmpty()) {
            throw new OrderDetailsServiceException("OrderDetail is not in the database");
        }
        return orderDetail;
    }

    /**
     * Finds order details from a certain order.
     * 
     * @param order with the order details
     * @return list of order details
     */
    @Override
    public List<OrderDetails> findByOrder(Order order) {
        return orderDetailsRepo.findByOrderID(order);
    }

    /**
     * Finds order details with a certain product in it.
     * 
     * @param product which should be included
     * @return list of order details
     */
    @Override
    public List<OrderDetails> findByProduct(Product product) {
        return orderDetailsRepo.findByProduct(product);
    }

    /**
     * Finds order details with a ceratin status.
     * 
     * @param status which should be included
     * @return list of order details
     */
    @Override
    public List<OrderDetails> findByStatus(Status status) {
        return orderDetailsRepo.findByStatusID(status);
    }

    /**
     * Deletes a certain order detail.
     * 
     * @param id which should get deleted
     */
    @Override
    public void deleteOrderDetail(long id) {
        Optional<OrderDetails> od = findByID(id);
        if(od.isPresent()){
            orderDetailsRepo.delete(od.get());
        }
        
    }

}
