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


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired UserService userService;

    static final Logger orderServiceLogger = org.slf4j.LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order editOrder(Order editOrder) {
       try{
           editOrder = orderRepo.save(editOrder); 
       }catch(OptimisticLockException ole){
           throw new OrderServiceException("Failed to save edited order");
       }

        return editOrder;
    }

    @Override
    public Order saveNewOrder(Order newOrder) throws OrderServiceException, UserServiceException{
        Order savedorder = null; 
        User user;
       try{
            savedorder= orderRepo.save(newOrder);
            if(savedorder.getUser() == null){
               user = userService.searchUserWithEmail(savedorder.getCustomerEmail());
               savedorder.setUser(user);
            }else{
                user= savedorder.getUser();
            }
           user.getOrders().add(savedorder);

       }catch(OptimisticLockException ole){
           orderServiceLogger.error("Order could not be saved.");
           throw new OrderServiceException("Order could not be saved.");
           
       }
        return savedorder;
    }

    @Override
    public Order findOrderByOrderNR(long orderNR) {
        Optional<Order> order = orderRepo.findByOrderNR(orderNR);
        return order.isPresent()? order.get():null;
    }

    @Override
    public List<Order> findAllCustomerOrders(User customer) {
        return orderRepo.findByUser(customer);
    }

    @Override
    public List<Order> getAllOrdersSortedByDate() {
        return orderRepo.findAllByOrderByDeliveryDateAsc();
    }

    @Override
    public List<Order> findAllOrdersOnDate(LocalDate date) {
        return orderRepo.findByDeliveryDate(date);
    }

    @Override
    public List<Order> findAllCustomerOrdersViaEmail(String email) {
        return orderRepo.findByCustomerEmail(email);
    }

 
    
}
