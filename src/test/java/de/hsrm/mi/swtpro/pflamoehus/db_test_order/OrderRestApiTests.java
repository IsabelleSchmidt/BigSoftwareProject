package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.adressservice.AdressService;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.BankcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice.BankcardService;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice.CreditcardService;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.rolesservice.RoleService;
import de.hsrm.mi.swtpro.pflamoehus.user.userapi.UserRestApi;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderapi.OrderRestApi;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.order.orderservice.OrderService;
import de.hsrm.mi.swtpro.pflamoehus.order.status.StatusRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice.StatusService;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest.ProductDTO;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailServiceImpl;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class OrderRestApiTests {

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    AdressService adressSerivce;
    
    @Autowired AdressRepository adressRepo;

	@Autowired
    BankcardService bankcardSerivce;
    
    @Autowired BankcardRepository bankrepo;

	@Autowired
    CreditcardService creditcardService;
    
    @Autowired CreditcardRepository creditrepo;

	@Autowired
    UserDetailServiceImpl uds;

    @Autowired OrderRestApi orderController;

    @Autowired UserRepository userRepo;

    @Autowired PasswordEncoder pe;

    @Autowired OrderRepository orderRepo;

    @Autowired MockMvc mockmvc;

    @Autowired ProductRepository productRepo;

    @Autowired
    ProductService productService;

    @Autowired UserRestApi userController;

    @Autowired RoleService roleService;

    @Autowired RolesRepository rolerepo;

    @Autowired OrderService orderService;

    @Autowired
    StatusService statusService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired OrderDetailsRepository orderDetailsRepo;

    @Autowired StatusRepository statusRepo;

   
    private final String PASSWORD_EXISTING = "UserPflamoehus1!";
    private final String EMAIL_EXISTING = "123@hsrm.de";
   


    @Test
    public void basecheck(){
        assertThat(orderController).isNotNull();
        assertThat(userRepo).isNotNull();
        assertThat(mockmvc).isNotNull();
        System.out.println("ORDERS: "+orderRepo.count());
        System.out.println("USERS: "+userRepo.count());
        System.out.println("PRODUCTS: "+productRepo.count());
    } 
    
    
  
     public JwtResponse login_user()throws Exception{

        
        //create a login request from an existing User
        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setPassword(PASSWORD_EXISTING); 
        loginrequest.setEmail(EMAIL_EXISTING);

        //Use ObjectMapper to create JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson =ow.writeValueAsString(loginrequest);

        //send loginrequest and expect it to be successful - should return JwtResponse
        String response = this.mockmvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        //Parse the string to a JwtResponse
        JwtResponse jwt = new ObjectMapper().readValue(response, JwtResponse.class);
       
        //check that jwt is actually a JwtResponse
        assertThat(jwt).isInstanceOf(JwtResponse.class);

        return jwt;
    }

    public OrderRequest fillDTO() throws Exception{

        OrderRequest orderDTO = new OrderRequest();

        //create productDTOs and add them to a list
        //articlenr from data.sql
        ProductDTO firstProduct = new ProductDTO(2,62);
        ProductDTO secondProduct = new ProductDTO(1,74);
        List<ProductDTO> allProducts = new ArrayList<>();
        allProducts.add(firstProduct);
        allProducts.add(secondProduct);
    
        //add all the attributes to the OrderDTO
        orderDTO.setAllProductsOrdered(allProducts);
        orderDTO.setJwtToken(login_user());
        orderDTO.setPriceTotal(42); //Not the real value of the productlist, but a testvalue

        return orderDTO;
    }


    @Test
    @Transactional
    @DisplayName("/api/order/new should save the given order and return an orderNR")
    public void postNewOrder() throws Exception{

        orderRepo.deleteAll();

        assertThat(orderRepo.count()).isEqualTo(0);

         //Use ObjectMapper to create JSON
         ObjectMapper mapper = new ObjectMapper();
         mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
         ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
         String requestJson =ow.writeValueAsString(fillDTO());
 
        System.out.println("REQUESTORDER: "+requestJson);
        System.out.print("ORDER: "+mockmvc.perform(post("/api/order/new").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString());

        assertThat(orderRepo.count()).isEqualTo(1);

        
    }

    @Test
    @Transactional
    @DisplayName("DELETE api/order/delete/{orderNR} should delete the order out of the repository and return true")
    public void deleteOrder() throws Exception{
        //orderNR 1 comes from datasql 
        postNewOrder();
        assertThat(orderRepo.count()).isGreaterThan(0);

        List<Order> allorders = orderRepo.findAll();
        for(Order order : allorders){ 

            long nrOrders = orderRepo.count();
            assertThat(mockmvc.perform(delete("/api/order/delete/"+order.getOrderNR())).andExpect(status().isOk()).andReturn()).isNotNull();
            assertThat(orderRepo.count()).isEqualTo(nrOrders-1);
        }
        
    }

 
    

}
