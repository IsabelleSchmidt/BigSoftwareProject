package de.hsrm.mi.swtpro.pflamoehus.order.orderapi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
import de.hsrm.mi.swtpro.pflamoehus.exceptions.ItemNotAvailableException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderDetailsServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.StatusServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
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
 * @version 3
 */
@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderRestApi {

    @Autowired
    OrderService orderService;

    @Autowired
    StatusService statusService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestApi.class);

    /*
     * OrderMessage for sending a OrderRequest.
     * 
     * @author Svenja Schenk
     * 
     * @version 1
     */
    private class OrderMessage {
        private String field;
        private String message;
        private long orderid = -1;

        /**
         * Constructor
         * 
         * @param orderid id
         */
        public OrderMessage(long orderid) {
            this.orderid = orderid;
        }

        /**
         * Constructor
         * 
         * @param field   given field
         * @param message given message
         */
        public OrderMessage(String field, String message) {
            this.field = field;
            this.message = message;

        }

        /**
         * OrderMessage as string
         */
        @Override
        public String toString() {
            return "OrderMessage [field=" + field + ", message=" + message + ", orderid=" + orderid + "]";
        }
        public long getOrderid() {
            return orderid;
        }

        public void setOrderid(long orderid) {
            this.orderid = orderid;
        }

     

    }

    /**
     * Delete order.
     * 
     * @param orderNR to be deleted
     * @return boolean
     */
    @DeleteMapping("/delete/{orderNR}")
    public boolean deleteOrder(@PathVariable long orderNR) {
        try {
            orderService.deleteOrder(orderNR);
        } catch (OrderServiceException ose) {
            LOGGER.error(ose.getMessage());
            return false;
        }

        return true;

    }

    /**
     * For accepting and saving a new order.
     * 
     * @param orderDTO new order
     * @param result   shows validation errors in the new order
     * @return orderMessage
     */
    @PostMapping("/new")
    public ResponseEntity<Set<OrderMessage>> newOrder(@Valid @RequestBody OrderRequest orderDTO, BindingResult result){
        
        Order order = new Order();
        Set<OrderMessage> allmessages = new HashSet<>();
        Set<OrderDetails> allDetails;
        User user;
        Status incoming; 
        String email;

        //search for mistakes in form
        if(result.hasErrors()){

            for(FieldError error : result.getFieldErrors()){
                allmessages.add(new OrderMessage(error.getField(), error.getDefaultMessage()));
            }

        }
        else{
            
                //Fill order table with infos of orderDTO (status, orderDetails, user, customerEmail, total price)

                try{
                    //find incoming status in the database, every new order gets this status   
                    incoming = statusService.findStatusWithCode(Statuscode.INCOMING.toString()); 
                    //Extract email out of JwtToken 
                    email = orderDTO.getJwtToken().getEmail();
                    //find user with that email in the database
                    user = userService.searchUserWithEmail(email); 
                    //Create matching OrderDetails for every productdto within the orderdto
    
                    //fill order with info
                    order.setStatus(incoming);
                    order.setCustomerEmail(email);
                    order.setUser(user);
                    order.setPriceTotal(orderDTO.getPriceTotal());

                    //Deliverydate is the date of the incoming order + 3 business days
                    order.setDeliveryDate(calcDeliveryDate());

                    //save filled order into the database
                    order = orderService.editOrder(order);
              
                    //create all OrderDetails with needed info
                    allDetails = createDetails(orderDTO,order,incoming);
              
                    //add order to the user and status, bidirectional relationships
                    incoming.getAllOrders().add(order);
                    user.getAllOrders().add(order);

                    //add orderdetails to order and status
                    order.setOrderdetails(allDetails);
                    incoming.setAllOrdersDetails(allDetails);

                    //add ordernr to the OrderMessage
                    allmessages.add(new OrderMessage(order.getOrderNR()));  
            
            } catch(StatusServiceException sse){
                    LOGGER.error(sse.getMessage());
                    allmessages.add(new OrderMessage("status",sse.getMessage()));
                    return ResponseEntity.ok().body(allmessages);

                } catch(UserServiceException use){
                    LOGGER.error(use.getMessage());
                    allmessages.add(new OrderMessage("user",use.getMessage()));
                    return ResponseEntity.ok().body(allmessages);

                } catch(ProductServiceException pse){
                    LOGGER.error(pse.getMessage());
                    allmessages.add(new OrderMessage("product",pse.getMessage()));
                    return ResponseEntity.ok().body(allmessages);

                }catch(ItemNotAvailableException inae){
                    LOGGER.error(inae.getMessage());
                    allmessages.add(new OrderMessage("emptyproduct",inae.getMessage()));
                    return ResponseEntity.ok().body(allmessages);

                }catch(OrderDetailsServiceException odse){
                    LOGGER.error(odse.getMessage());
                    allmessages.add(new OrderMessage("orderdetails", odse.getMessage()));
                    return ResponseEntity.ok().body(allmessages);
                     
                }catch(OrderServiceException ose){
                    allmessages.add(new OrderMessage("order",ose.getMessage()));
                    LOGGER.error(ose.getMessage());
                    return ResponseEntity.ok().body(allmessages);

                }
            }              
        return ResponseEntity.ok().body(allmessages);
    }

    /**
     * Gets all orders with a certain date.
     * 
     * @param duedate date
     * @return all orders
     */
    @GetMapping("/all/{duedate}")
    public List<Order> getallOrdersWithDueDate(@PathVariable String duedate) {

        String[] dates = duedate.split("\\p{Punct}");
        if(dates.length == 3){
             LocalDate date = LocalDate.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
             return orderService.findAllOrdersOnDate(date);
        }else{
            //When the date is invalid, return null, if no order is found the list is empty
            
            return new ArrayList<>();
        }

    }

    /**
     * For editing a orderstatus.
     * 
     * @param orderNR   order
     * @param newStatus new Status
     * @return boolean
     */
    @PostMapping("/edit/orderstatus/{orderNR}/{newStatus}")
    public boolean changeOrderStatus(@PathVariable long orderNR, @PathVariable String newStatus) {
        Order order;
        Status status;
        
        try{
            order = orderService.findOrderByOrderNR(orderNR);
            status = statusService.findStatusWithCode(newStatus);
            order.setStatus(status);

            for(OrderDetails detail : order.getOrderdetails()){
                detail.setStatusID(status);
            }

        }catch(OrderServiceException ose){
            LOGGER.error(ose.getMessage());
            return false;
        }catch(StatusServiceException sse){
            LOGGER.error(sse.getMessage());
            return false;
        }
       
        return true;
    }

    /**
     * Calculates the deliverydate by adding three business days to today's date.
     */
    private LocalDate calcDeliveryDate() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        LocalDate deliverydate;
        if (today == DayOfWeek.MONDAY || today == DayOfWeek.TUESDAY || today == DayOfWeek.WEDNESDAY
                || today == DayOfWeek.SUNDAY) {
            deliverydate = LocalDate.now().plusDays(3);
        } else {
            deliverydate = LocalDate.now().plusDays(4);
        }

        return deliverydate;
    }

    /**
     * Creating new OrderDetails
     * 
     * @param orderDTO corresponding order
     * @param order    order
     * @param incoming status
     * @return OrderDetails
     * @throws ProductServiceException
     * @throws OrderDetailsServiceException
     */
    private Set<OrderDetails> createDetails(OrderRequest orderDTO, Order order, Status incoming){
        
        Set<OrderDetails> allDetails = new HashSet<>();
        Product product;

        for (OrderRequest.ProductDTO productdto : orderDTO.getAllProductsOrdered()) {
            OrderDetails detail = new OrderDetails();

            // find corresponding product in database using productDTO articleNR
            product = productService.searchProductwithArticleNr(productdto.getArticleNR());
            if(productdto.getAmount() > product.getAvailable()){
                throw new ItemNotAvailableException(product.getArticlenr()+" -- Es sind nur noch : -- " +product.getAvailable()+" verfügbar.");
            }

            //Set mandatory attributes of an OrderDetail
            detail.setProduct(product);
            detail.setProductAmount(productdto.getAmount());
            detail.setOrderID(order);
            detail.setStatusID(incoming);
            detail = orderDetailsService.editOrderDetail(detail);
            allDetails.add(detail);

            //Set bidirectional relationships and reduce number of available products 
            product.getAllOrderDetails().add(detail);
            product.setAvailable(product.getAvailable()-productdto.getAmount());

        }

        return allDetails;
    }
}
