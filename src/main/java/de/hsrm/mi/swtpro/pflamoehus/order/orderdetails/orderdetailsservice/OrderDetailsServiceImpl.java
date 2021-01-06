package de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderDetailsServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepo;

    private static final Logger ORDERDETAILSSERVICELOGGER = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    @Override
    public OrderDetails editOrderDetail(OrderDetails orderDetail) {
        
        try{
            orderDetail = orderDetailsRepo.save(orderDetail);
        }catch(OptimisticLockException oLE){
            ORDERDETAILSSERVICELOGGER.error("OrderDetails can only be edited by one person at a time.");
            throw new OrderDetailsServiceException();

        }

        return orderDetail;
    }

    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepo.findAll();
    }

    @Override
    public Optional<OrderDetails> findByID(long id) {
        Optional<OrderDetails> orderDetail = orderDetailsRepo.findByOrderDetailsID(id);
        if(orderDetail.isEmpty()){
            throw new OrderDetailsServiceException("OrderDetail is not in the database");
        }
        return orderDetail;
    }

    @Override
    public List<OrderDetails> findByOrder(Order order) {
        return orderDetailsRepo.findByOrderID(order);
    }

    @Override
    public List<OrderDetails> findByProduct(Product product) {
        return orderDetailsRepo.findByProduct(product);
    }

    @Override
    public List<OrderDetails> findByStatus(Status status) {
        return orderDetailsRepo.findByStatusID(status);
    }

    @Override
    public void deleteOrderDetail(long id) {
        Optional<OrderDetails> od = findByID(id);
        orderDetailsRepo.delete(od.get());
    }
    
}
