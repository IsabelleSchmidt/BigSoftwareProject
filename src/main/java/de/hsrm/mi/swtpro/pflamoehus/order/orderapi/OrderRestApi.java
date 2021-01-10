package de.hsrm.mi.swtpro.pflamoehus.order.orderapi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderDetailsServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.StatusServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.order.orderservice.OrderService;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;
import de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice.StatusService;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

/*
 * OrderRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderRestApi {

    @Autowired
    OrderService orderService;

    @Autowired StatusService statusService;

    @Autowired JwtUtils jwtUtils;

    @Autowired UserService userService;

    @Autowired OrderDetailsService orderDetailsService;

    @Autowired ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestApi.class);

    private class OrderMessage{
        private String field;
        private String message;
        private long orderid;

        public OrderMessage(long orderid){
            this.orderid = orderid;
        }
        
        public OrderMessage(String field, String message){
            this.field = field;
            this.message = message;
           

        }

        @Override
        public String toString() {
            return "OrderMessage [field=" + field + ", message=" + message + ", orderid="+ orderid+"]";
        }

    }

    /** 
     * Delete order.
     * 
     * @return boolean
     */
    @DeleteMapping("/delete/{orderNR}")
    public boolean deleteOrder(@PathVariable long orderNR){
        try{
            orderService.deleteOrder(orderNR);
        }catch(OrderServiceException ose){
            LOGGER.error(ose.getMessage());
            return false;
        }
        
        return true;
      
    }


    @PostMapping("/new")
    public ResponseEntity<Set<OrderMessage>> newOrder(@Valid @RequestBody OrderRequest orderDTO, BindingResult result){

        
        Order order = new Order();
        Set<OrderMessage> allmessages= new HashSet<>();
        Set<OrderDetails> allDetails;
        User user;
        Status incoming; 

        //search for mistakes in form
        if(result.hasErrors()){
            for(FieldError error : result.getFieldErrors()){
                allmessages.add(new OrderMessage(error.getField(), error.getDefaultMessage()));
            }

        }
        else{
            
            //Fill order table with infos of orderDTO (status, orderDetails, user, customerEmail, total price)

            //find incoming status in the database, every new order gets this status
            try{
                 incoming = statusService.findStatusWithCode(Statuscode.INCOMING.toString());
            } catch(StatusServiceException sse){
                LOGGER.error(sse.getMessage());
                allmessages.add(new OrderMessage("status",sse.getMessage()));
                return ResponseEntity.ok().body(allmessages);
            }

            //Status of a new order is incoming
            order.setStatus(incoming);

            //Extract email out of JwtToken
            String token = orderDTO.getJwtToken().toString();
            String email = jwtUtils.getUserNameFromJwtToken(token);

            //find user with that email in the database
            try{
                user = userService.searchUserWithEmail(email);
               
            }catch(UserServiceException use){
                LOGGER.error(use.getMessage());
                allmessages.add(new OrderMessage("user",use.getMessage()));
                return ResponseEntity.ok().body(allmessages);
            } 

            //add user, pricetotal and email to the order table
            order.setCustomerEmail(email);
            order.setUser(user);
            order.setPriceTotal(orderDTO.getPriceTotal());

            //Deliverydate is the date of the incoming order + 3 business days
            order.setDeliveryDate(calcDeliveryDate());

            //save filled order into the database
            try{
                order = orderService.editOrder(order);
            }catch(OrderServiceException ose){
                allmessages.add(new OrderMessage("order",ose.getMessage()));
                LOGGER.error(ose.getMessage());
                return ResponseEntity.ok().body(allmessages);

            }catch(UserServiceException use){
                allmessages.add(new OrderMessage("user", use.getMessage()));
                LOGGER.error(use.getMessage());
                return ResponseEntity.ok().body(allmessages);
            }
            
            //add order to the user and status, bidirectional relationships
            incoming.getAllOrderNR().add(order);
            user.getOrders().add(order);

            //Create matching OrderDetails for every productdto within the orderdto
            try{
                allDetails = createDetails(orderDTO, order, incoming);

            }catch(ProductServiceException pse){

                LOGGER.error(pse.getMessage());
                allmessages.add(new OrderMessage("product", pse.getMessage()));
                return ResponseEntity.ok().body(allmessages);

            }catch(OrderDetailsServiceException odse){

                LOGGER.error(odse.getMessage());
                allmessages.add(new OrderMessage("OrderDetails",odse.getMessage()));
                return ResponseEntity.ok().body(allmessages);
            }
            
            //Add OrderDetails to the order and the incoming status
            order.setOrderdetails(allDetails);
            incoming.setAllOrderDetailNR(allDetails);
            allmessages.add(new OrderMessage(order.getOrderNR()));   

        }

        return ResponseEntity.ok().body(allmessages);
    }


    @GetMapping("/all/{duedate}")
    public List<Order> getallOrdersWithDueDate(@PathVariable String duedate){

        //TODO: invalid date zurueckgeben
        String[] dates = duedate.split("\\p{Punct}");
        if(dates.length == 3){
             LocalDate date = LocalDate.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
             return orderService.findAllOrdersOnDate(date);
        }else{
            return null;
        }
      
    }

    @PostMapping("/edit/orderstatus/{orderNR}/{newStatus}")
    public boolean changeOrderStatus(@PathVariable long orderNR, @PathVariable String newStatus){

        //TODO: order aus altem status raus, in neuen Status rein, in order auch aendern

        return true;
    }
    

    /**
     * Calculates the deliverydate by adding three business days to today's date.
     */
    private LocalDate calcDeliveryDate(){
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        LocalDate deliverydate;
        if(today == DayOfWeek.MONDAY || today == DayOfWeek.TUESDAY || today == DayOfWeek.WEDNESDAY || today == DayOfWeek.SUNDAY){
            deliverydate = LocalDate.now().plusDays(3);
        }else{
            deliverydate = LocalDate.now().plusDays(4);
        }

        return deliverydate;
    }

    private Set<OrderDetails> createDetails(OrderRequest orderDTO, Order order, Status incoming) throws ProductServiceException, OrderDetailsServiceException{
        Set<OrderDetails> allDetails = new HashSet<>();
        Product product;

        for(OrderRequest.ProductDTO productdto : orderDTO.getAllProductsOrdered()){
            OrderDetails details = new OrderDetails();
           

            //find corresponding product in database using productDTO articleNR
            product = productService.searchProductwithArticleNr(productdto.getArticleNR());
           

            //Set mandatory attributes of an OrderDetail
            details.setOrderID(order);
            details.setStatusID(incoming);   
            details.setProduct(product);
            details.setProductAmount(productdto.getAmount());
           

            //save the new orderdetails in repository
            details = orderDetailsService.editOrderDetail(details);
            allDetails.add(details);

            //Set bidirectional relationships and reduce number of available products 
            product.getAllOrderDetails().add(details);
            product.setNrAvailableItems(product.getNrAvailableItems()-productdto.getAmount());

        }   

        return allDetails;
    }
}
