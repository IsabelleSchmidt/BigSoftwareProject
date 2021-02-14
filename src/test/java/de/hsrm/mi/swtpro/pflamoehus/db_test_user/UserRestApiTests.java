package de.hsrm.mi.swtpro.pflamoehus.db_test_user;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.NewPasswordRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStoreRepository;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStoreService;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
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
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidEmail;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidPassword;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserRestApiTests {

    private final String FIRSTNAME_NEW = "Olaf der Dritte";
    private final String GENDER_NEW = "DIVERSE";
    private final String PASSWORD_NEW = "HaaaaaaaHA11!";
    private final String LASTNAME_NEW = "Schmidt";
    private final String BIRTHDAY_NEW = "1999-01-01";
    private final String EMAIL_NEW = "hansolaf@hs-rm.de";
    private final String PASSWORD_EXISTING = "UserPflamoehus1!";
    private final String EMAIL_EXISTING = "user@pflamoehus.de";
    private final String PATH = "/api/user";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdressService adressSerivce;

    @Autowired
    AdressRepository adressRepo;

    @Autowired
    BankcardService bankcardSerivce;

    @Autowired
    BankcardRepository bankrepo;

    @Autowired
    CreditcardService creditcardService;

    @Autowired
    CreditcardRepository creditrepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder pe;

    @Autowired
    MockMvc mockmvc;

    @Autowired
    UserRestApi userController;

    @Autowired
    RoleService roleService;

    @Autowired
    RolesRepository rolerepo;

    @Autowired
    JwtStoreRepository jwtStoreRepo;

    @Autowired
    JwtStoreService jwtStoreService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    ProductService productService;

    private class SignupDTO {

        @Size(min = 3)
        private String firstName;

        @Size(min = 3)
        private String lastName;

        @ValidEmail
        private String email;

        private String birthdate;

        private String gender;

        @ValidPassword
        private String password;

        private List<Roles> roles;

        public SignupDTO(String firstName, String lastName, String email, String birthdate, String gender,
                String password) {
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

    @AfterEach
    public void clearRepos() {
        creditrepo.deleteAll();
        bankrepo.deleteAll();
        adressRepo.deleteAll();
        userRepo.deleteAll();
        jwtStoreRepo.deleteAll();

    }

    @Test
    public void basecheck() {
        assertThat(userController).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName(" Sign up a new User")
    public void successful_register_adds_user_to_database() throws Exception {
        // create Signuprequest
        SignupDTO request = new SignupDTO(FIRSTNAME_NEW, LASTNAME_NEW, EMAIL_NEW, BIRTHDAY_NEW, GENDER_NEW,
                PASSWORD_NEW);

        // Use ObjectMapper to create JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        // send signuprequest
        mockmvc.perform(post(PATH + "/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        // check whether the user was successfully signed up
        assertEquals(1, userRepo.count());

        // Wrong attributes should return where the validation failed and not add user
        // to database
        request.setEmail("email");
        requestJson = ow.writeValueAsString(request);
        MvcResult result = mockmvc
                .perform(post(PATH + "/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).contains("field").contains("email");
        assertEquals(1, userRepo.count());

    }

    @Test
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Transactional
    @DisplayName("Login an existing user with POST /api/user/login")
    public void login_returns_JwtResult_when_successful() throws Exception {

        assertThat(userRepo.count()).isGreaterThan(0);

        // create a login request from an existing User
        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setPassword(PASSWORD_EXISTING);
        loginrequest.setEmail("a" + EMAIL_EXISTING);

        // Use ObjectMapper to create JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginrequest);

        // send wrong loginreuest and expect it to have 406 status
        mockmvc.perform(post(PATH + "/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().is(406)).andReturn().getResponse();

        // send correct loginrequest and expect it to be successful
        loginrequest.setEmail(EMAIL_EXISTING);
        requestJson = ow.writeValueAsString(loginrequest);
        MvcResult result = mockmvc
                .perform(post(PATH + "/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        // check that response is a jwtresult
        assertThat(result.getResponse().getContentAsString()).contains("accessToken").contains("email")
                .contains("roles");

    }

    @Test
    @DisplayName("GET /api/user/getUser should return a User")
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Transactional
    @WithMockUser(username = EMAIL_EXISTING, password = PASSWORD_EXISTING)
    public void getAdress_returns_user_from_context_if_user_is_logged_in() throws Exception {

        assertThat(userRepo.count()).isGreaterThan(0);

        // get user (returned null),ontext haelt anonymous user -> nicht in DB zu
        // finden, result ist leer

        MvcResult result = mockmvc.perform(get(PATH + "/getUser")).andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("email").contains("creditcard")
                .contains("bankcard").contains("password").contains("allAdresses").contains("firstName")
                .contains("lastName").contains("birthdate").contains("gender").contains("roles");

    }

    @Test
    @DisplayName("GET api/user/getUser should return null")
    @Transactional
    public void getAdress_returns_null_if_no_User_is_logged_in() throws Exception {

        MvcResult result = mockmvc.perform(get(PATH + "/getUser")).andExpect(status().isOk()).andReturn();
        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("POST /api/user/logout should clear security context and delete token from store")
    @Transactional
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = EMAIL_EXISTING, password = PASSWORD_EXISTING)
    public void context_cleared_after_logout() throws Exception {

        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setPassword(PASSWORD_EXISTING);
        loginrequest.setEmail(EMAIL_EXISTING);

        // Use ObjectMapper to create JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginrequest);

        assertEquals(0, jwtStoreRepo.count());

        MvcResult result = mockmvc
                .perform(post(PATH + "/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();
        String token = result.getResponse().getContentAsString();

        JwtResponse response = mapper.readValue(token, JwtResponse.class);

        assertEquals(1, jwtStoreRepo.count());

        mockmvc.perform(post(PATH + "/logout").header("Authorization", "Bearer " + response.getAccessToken()))
                .andExpect(status().isOk()).andReturn();
        assertEquals(0, jwtStoreRepo.count());
        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("GET checkByEmail/{email} should return a empty MessageResponse")
    @WithMockUser(username = EMAIL_EXISTING, password = PASSWORD_EXISTING)
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void check_if_user_exists() throws Exception {

        assertThat(userRepo.count()).isGreaterThan(0);

        MvcResult result = mockmvc.perform(get(PATH + "/checkByEmail/{email}", EMAIL_EXISTING))
                .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString().equals(""));

        // Wrong User
        MvcResult result1 = mockmvc.perform(get(PATH + "/checkByEmail/{email}", EMAIL_EXISTING + "a"))
                .andExpect(status().isOk()).andReturn();
        assertThat(!(result1.getResponse().getContentAsString().equals("")));
    }

    @Test
    @DisplayName("POST /changePassword should deliver a changed password with the User")
    @WithMockUser(username = EMAIL_EXISTING, password = PASSWORD_EXISTING)
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void change_password() throws Exception {

        assertThat(userRepo.count()).isGreaterThan(0);

        User user = userService.searchUserWithEmail(EMAIL_EXISTING);
        // encoded password
        String oldPasssword = user.getPassword();

        // correct password
        // NewPasswordRequest
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest();
        newPasswordRequest.setEmail(EMAIL_EXISTING);
        newPasswordRequest.setPassword("GutesPasswort123!");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(newPasswordRequest);

        mockmvc.perform(
                post(PATH + "/changePassword").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String newPassword = user.getPassword();

        assertThat((!oldPasssword.equals(newPassword)));

    }

    @Test
    @DisplayName("POST /changePassword should deliver the same password ")
    @WithMockUser(username = EMAIL_EXISTING, password = PASSWORD_EXISTING)
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void change_password_wrong() throws Exception {

        assertThat(userRepo.count()).isGreaterThan(0);

        User user = userService.searchUserWithEmail(EMAIL_EXISTING);
        // encoded password
        String oldPasssword = user.getPassword();

        // correct password
        // NewPasswordRequest
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest();
        newPasswordRequest.setEmail(EMAIL_EXISTING);
        newPasswordRequest.setPassword("nein");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(newPasswordRequest);

        mockmvc.perform(
                post(PATH + "/changePassword").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String newPassword = user.getPassword();

        assertThat(oldPasssword.equals(newPassword));

    }
}
