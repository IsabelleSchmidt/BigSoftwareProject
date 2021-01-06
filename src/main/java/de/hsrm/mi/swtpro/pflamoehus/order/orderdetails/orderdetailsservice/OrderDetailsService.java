package de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

public interface OrderDetailsService {

    OrderDetails editOrderDetail(OrderDetails orderDetail);

    List<OrderDetails> findAll();

    Optional<OrderDetails> findByID(long id);

    List<OrderDetails> findByOrder(Order order);

    List<OrderDetails> findByProduct(Product product);

    List<OrderDetails> findByStatus(Status status);

    void deleteOrderDetail (long id);



    
}
