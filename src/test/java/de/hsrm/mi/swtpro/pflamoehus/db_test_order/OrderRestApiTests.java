package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
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
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.order.orderservice.OrderService;
import de.hsrm.mi.swtpro.pflamoehus.order.status.StatusRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;
import de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice.StatusService;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest.ProductDTO;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagRepository;
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

    @Autowired PictureRepository pictureRepo;

    @Autowired TagRepository tagRepo;

   
    private final String PASSWORD_EXISTING = "UserPflamoehus1!";
    private final String EMAIL_EXISTING = "user@pflamoehus.de";
   
    @BeforeEach
    public void clearRepos(){

        adressRepo.deleteAll();
        bankrepo.deleteAll();
        creditrepo.deleteAll();
        userRepo.deleteAll();
        statusRepo.deleteAll();
        tagRepo.deleteAll();
        pictureRepo.deleteAll();
        productRepo.deleteAll();
        orderDetailsRepo.deleteAll();
        orderRepo.deleteAll();
    }

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
        // orderDTO.setJwtToken(login_user());
        orderDTO.setPriceTotal(42); //Not the real value of the productlist, but a testvalue

        return orderDTO;
    }


    @Test
    @Transactional
    @DisplayName("/api/order/new should save the given order and return an orderNR")
    public void postNewOrder() throws Exception{

        orderRepo.deleteAll();
        String token = login_user().getAccessToken();
        assertThat(orderRepo.count()).isEqualTo(0);

         //Use ObjectMapper to create JSON
         ObjectMapper mapper = new ObjectMapper();
         mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
         ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
         String requestJson =ow.writeValueAsString(fillDTO());
 
        System.out.println("REQUESTORDER: "+requestJson);
        System.out.print("ORDER: "+mockmvc.perform(post("/api/order/new").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token).content(requestJson))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString());

        assertThat(orderRepo.count()).isEqualTo(1);

        
    }

    @Test
    @Transactional
    @Sql("/data.sql")
    @DisplayName("DELETE api/order/delete/{orderNR} should delete the order out of the repository and return true")
    public void deleteOrder() throws Exception{
        //orderNR 1 comes from datasql 
        assertThat(orderRepo.count()).isGreaterThan(0);

        List<Order> allorders = orderRepo.findAll();
        long nrOrders = orderRepo.count();
        for(Order order : allorders){ 

            assertThat(mockmvc.perform(delete("/api/order/delete/"+order.getOrderNR())).andExpect(status().isOk()).andReturn()).isNotNull();
            assertThat(orderRepo.count()).isEqualTo(nrOrders-1);
            nrOrders -=1;
        }
        
    }

    @Test
    @Transactional
    @Sql("/data.sql")
    @DisplayName("POST /api/order/status/newstatus muss status der bestellung neusetzen")
    public void test_change_status() throws Exception{

        final Statuscode code = Statuscode.INPROGRESS;

        assertThat(orderRepo.count()).isGreaterThan(0);
        Order order = orderService.findOrderByOrderNR(1);
        order.setStatus(statusService.findStatusWithCode(Statuscode.INCOMING));

        String correctget = mockmvc.perform(get("/api/order/edit/orderstatus/"+order.getOrderNR()+"/"+code.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String incorrectget_unkownOrderNr = mockmvc.perform(get("/api/order/edit/orderstatus/"+0+"/"+code.toString())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockmvc.perform(get("/api/order/edit/orderstatus/"+1+"FALSCH")).andExpect(status().isNotFound());
    
        assertThat(correctget).contains("true");
        assertThat(incorrectget_unkownOrderNr).contains("false");
        assertEquals(code, order.getStatus().getStatuscode());

        for (OrderDetails detail : order.getOrderdetails()){
            assertEquals(code, detail.getStatusID().getStatuscode());
        }
        
    
    }

 
    

}
