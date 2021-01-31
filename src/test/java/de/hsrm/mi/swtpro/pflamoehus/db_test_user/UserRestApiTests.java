package de.hsrm.mi.swtpro.pflamoehus.db_test_user;

import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.adressservice.AdressService;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.BankcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice.BankcardService;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice.CreditcardService;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.rolesservice.RoleService;
import de.hsrm.mi.swtpro.pflamoehus.user.userapi.UserRestApi;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

@SpringBootTest(webEnvironment =WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserRestApiTests {

    private final String FIRSTNAME_NEW = "Olaf der Dritte";
    private final String GENDER_NEW = "DIVERSE";
    private final String PASSWORD_NEW = "HaaaaaaaHA11!";
    private final String LASTNAME_NEW = "Schmidt";
    private final String BIRTHDAY_NEW = "1999-01-01";
    private final String EMAIL_NEW = "hansolaf@hs-rm.de";
    private final String PASSWORD_EXISTING = "UserPflamoehus1!";
    private final String EMAIL_EXISTING = "user@pflamoehus.de";

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


    @Autowired UserRepository userRepo;

    @Autowired PasswordEncoder pe;

    @Autowired MockMvc mockmvc;

    @Autowired UserRestApi userController;

    @Autowired RoleService roleService;

    @Autowired RolesRepository rolerepo;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    ProductService productService;

    
    
    private class SignupDTO{

        private String firstName;

        private String lastName;
    
        private String email;
    
        private String birthdate;
      
        private String gender;
    
        private String password;
    
        private List<Roles> roles;
    
        public SignupDTO(String firstName, String lastName, String email, String birthdate, String gender, String password){
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthdate = birthdate;
            this.password = password;
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<Roles> getRoles() {
            return roles;
        }

        public void setRoles(List<Roles> roles) {
            this.roles = roles;
        }
        
    } 

    @BeforeEach
    public void clearRepos(){
        creditrepo.deleteAll();
        bankrepo.deleteAll();
        adressRepo.deleteAll();
        userRepo.deleteAll();
    
    }

    @Test
    public void basecheck(){
        assertThat(userController).isNotNull();
    }


    @Test
    @Transactional
    @DisplayName(" Sign up a new User")
    public void test_register_UserRestApi() throws Exception{
        userRepo.deleteAll();
       //create Signuprequest
        SignupDTO request = new SignupDTO(FIRSTNAME_NEW, LASTNAME_NEW, EMAIL_NEW, BIRTHDAY_NEW, GENDER_NEW, PASSWORD_NEW);
     
       //Use ObjectMapper to create JSON
       ObjectMapper mapper = new ObjectMapper();
       mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
       ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
       String requestJson=ow.writeValueAsString(request);
   
     
       //send signuprequest
       mockmvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                       .andExpect(status().isOk()).andReturn();

       //check whether the user was successfully signed up
       assertThat(userRepo.count()).isEqualTo(1);
      
    }

     @Test
     @Sql("/data.sql")
     @DisplayName("Login an existing user with POST /api/user/login")
     public void login_user()throws Exception{

        assertThat(userRepo.count()).isGreaterThan(0);
        //TODO: gradle zum laden der datasql bringen
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
        mockmvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson)).andExpect(status().isOk()).andReturn();

    }
}
