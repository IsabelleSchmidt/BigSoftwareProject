package de.hsrm.mi.swtpro.pflamoehus.user.userapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.NewPasswordRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.SignUpRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.UserOrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.MessageResponse;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.AdressServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.BankcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.JwtStoreServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.api.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.AuthTokenFilter;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStore;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStoreService;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.*;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.adressservice.*;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.rolesservice.RoleService;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Ann-Cathrin Fabian
 * @version 7
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserRestApi {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AdressService adressSerivce;

	@Autowired
	BankcardService bankcardSerivce;

	@Autowired
	CreditcardService creditcardService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	JwtStoreService jwtStoreService;

	@Autowired
	AuthTokenFilter authTokenFilter;


	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestApi.class);

	/**
	 * PostMapping for login.
	 * 
	 * @param result       binding result
	 * @param loginRequest login values
	 * @return ResponseEntity
	 */
	@PostMapping("/login")
	@Transactional
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
			BindingResult result) {
		JwtStore jwtStore = new JwtStore();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		jwtStore.setToken(jwt);
		jwtStore = jwtStoreService.saveAccessToken(jwtStore);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		JwtResponse response = new JwtResponse(jwt, userDetails.getUsername(), roles);
		return ResponseEntity.ok(response);
	}

	/**
	 * PostMapping for registration.
	 * 
	 * @param signUpRequest given values of the user that wants to be registerd
	 * @param result        for controlling if the given SignUp request is valid
	 * @return ResponseEntity
	 */
	@PostMapping("/register")
	@Transactional
	public ResponseEntity<List<MessageResponse>> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
			BindingResult result) {
		MessageResponse mr = new MessageResponse();
		List<MessageResponse> mrs = new ArrayList<>();
		if (userService.existsByEmail(signUpRequest.getEmail())) {
			mr.setMessage("Email ist already taken.");
			mr.setField("email");
			mrs.add(mr);
			LOGGER.error("EMAIL IS ALREADY TAKEN.");
			return new ResponseEntity<>(mrs, HttpStatus.OK);
		}
		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				MessageResponse mrp = new MessageResponse();
				mrp.setMessage(error.getDefaultMessage());
				mrp.setField(error.getField());
				mrs.add(mrp);

			}

			return new ResponseEntity<>(mrs, HttpStatus.OK);

		}

		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setBirthdate(signUpRequest.getBirthdate());
		user.setGender(signUpRequest.getGender());
		user.setPassword(signUpRequest.getPassword());

		List<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRole = roleService.findByName(ERoles.USER);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":

						Roles adminRole = roleService.findByName(ERoles.ADMIN);
						roles.add(adminRole);

						break;
					case "staff":
						Roles modRole = roleService.findByName(ERoles.STAFF);
						roles.add(modRole);

						break;
					case "service":
						Roles serviceRole = roleService.findByName(ERoles.SERVICE);
						roles.add(serviceRole);

						break;
					case "warehouse":
						Roles warehouseRole = roleService.findByName(ERoles.WAREHOUSE);
						roles.add(warehouseRole);

						break;
					default:
						Roles userRole = roleService.findByName(ERoles.USER);
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userService.registerUser(user);
		return ResponseEntity.ok(mrs);

	}

	/**
	 * PostMapping for the data the user has to give while he is ordering.
	 * 
	 * @param userOrderRequest includes a token, paymentmethod and adress
	 * @param result           for showing mistakes in the formular
	 * @return response
	 * @throws AuthenticationException if the user is not known in the database
	 */
	@Transactional
	@PostMapping("/newOrder/user")
	public ResponseEntity<List<MessageResponse>> addInfosToUser(@Valid @RequestBody UserOrderRequest userOrderRequest,
			BindingResult result) throws AuthenticationException {
		List<MessageResponse> mrs = new ArrayList<>();
		LOGGER.info("NEW ORDER IN USERAPI");

		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				MessageResponse mrp = new MessageResponse();
				mrp.setMessage(error.getDefaultMessage());
				mrp.setField(error.getField());
				mrs.add(mrp);
			}
			if (mrs.toString().contains("cowner") && mrs.toString().contains("creditcardnumber")
					&& !(mrs.toString().contains("owner") && mrs.toString().contains("iban")
							&& mrs.toString().contains("bank"))) {
				List<MessageResponse> delete = mrs.stream()
						.filter(s -> "creditcard.cowner creditcard.creditcardnumber creditcard.dateOfExpiry"
								.contains(s.getField()))
						.collect(Collectors.toList());

				mrs.removeAll(delete);
			}

			if (mrs.toString().contains("iban") && mrs.toString().contains("owner") && mrs.toString().contains("bank")
					&& !(mrs.toString().contains("cowner") && mrs.toString().contains("creditcardnumber"))) {
				List<MessageResponse> delete = mrs.stream()
						.filter(s -> "bankCard.owner bankCard.bank bankCard.iban"
								.contains(s.getField()))
						.collect(Collectors.toList());

				mrs.removeAll(delete);
			}

			if (!mrs.isEmpty()) {
				return new ResponseEntity<>(mrs, HttpStatus.OK);
			}

		}
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			User user = userService.searchUserWithEmail(email); 

			if (userOrderRequest.getAdress() != null) {
			
				Adress newAdress = userOrderRequest.getAdress();
				newAdress = adressSerivce.saveAdress(newAdress); 
				user.getAllAdresses().add(newAdress); 
				user = userService.editUser(user); 

			}

			if (!userOrderRequest.getBankCard().getIban().equals("")) {
				Bankcard newBankcard = userOrderRequest.getBankCard();
				newBankcard.getUser().add(user);
				newBankcard = bankcardSerivce.saveBankcard(newBankcard);
				user.getBankcard().add(newBankcard);
				user = userService.editUser(user);
			}

			if (!userOrderRequest.getCreditcard().getCreditcardnumber().equals("")) {
				Creditcard newCreditcard = userOrderRequest.getCreditcard();
				newCreditcard.getUser().add(user);
				newCreditcard = creditcardService.saveCreditcard(newCreditcard);
				user.getCreditcard().add(newCreditcard);
				user = userService.editUser(user);
			}

		} catch (AdressServiceException ase) {
			LOGGER.error("Adress could not be saved.");
			throw new UserApiException("Adress couldn't be saved.");

		} catch (BankcardServiceException bse) {
			LOGGER.error("Bankcard couldn't be saved.");
			throw new UserApiException("Bankcard couldn't be saved.");

		} catch (CreditcardServiceException cse) {
			LOGGER.error("Creditcard couldn't be saved.");
			throw new UserApiException("Creditcard couldn't be saved.");

		} catch (UserServiceException use) {
			throw new AuthenticationException();
		}
		LOGGER.info("USER WURDE EINGESPEICHERT");
		return ResponseEntity.ok(mrs);
	}

	
	/** 
	 * Searches for a given user in the database.
	 * 
	 * @return found user
	 */
	@GetMapping("/getUser")
	@Transactional
	public User getUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user;
		
		try {
			user = userService.getFullyInitializedUser(email);
			
		} catch (UserServiceException use) {
			LOGGER.error(use.getMessage());
			return null;
		}
		return user;

	}

	
	/** 
	 * Logout a given user.
	 * 
	 * @param logoutRequest includes a token
	 * @param request important for the logout process
	 * @return a message
	 */
	@PostMapping("/logout")
	@Transactional
	public ResponseEntity<List<MessageResponse>> logoutUser(HttpServletRequest request){
		List<MessageResponse> mrs = new ArrayList<>();
		MessageResponse mr = new MessageResponse();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String token = authTokenFilter.parseJwt(request);
		if (auth != null) {
			try{
				new SecurityContextLogoutHandler().logout(request, null, auth);
				SecurityContextHolder.clearContext();
				jwtStoreService.deleteAccessToken(token);
			}catch(JwtStoreServiceException jsse){
				LOGGER.error("LOGOUT NOT SUCCESSFUL.");
				mr.setMessage("Fehler beim Ausloggen. Bitte loggen Sie sich zuerst ein.");
				mrs.add(mr);
			}
		}
		else{
			mr.setMessage("Fehler beim Ausloggen. Bitte loggen Sie sich zuerst ein.");
			mrs.add(mr);
		}
		LOGGER.info("LOGOUT SUCCESSFUL.");
		return ResponseEntity.ok(mrs);
	}

	@GetMapping("checkByEmail/{email}")
	public ResponseEntity<MessageResponse> checkIfUserExists (@PathVariable("email") String email) {
		MessageResponse mrp = new MessageResponse();
		boolean exists = userService.existsByEmail(email);

		if (exists) {
			return ResponseEntity.ok(mrp);
		} else {
			mrp.setMessage("UserNotFound");
			return ResponseEntity.ok(mrp);
		}
	}

	@PostMapping("/changePassword")
	@Transactional
	public ResponseEntity<MessageResponse> changePw(@Valid @RequestBody NewPasswordRequest newPasswordRequest, BindingResult result) {

		MessageResponse mrp = new MessageResponse();

		if (result.hasErrors()) {
			LOGGER.error("Error in change PW");
			for (FieldError error : result.getFieldErrors()) {
				LOGGER.error("Error in change PW: " + error.getDefaultMessage());
				mrp.setMessage("Das Passwort muss mindestens 8 Zeichen, mindestens 1 Sonderzeichen und mindestens 1 Zahl besitzen");
				return ResponseEntity.ok(mrp);
			}
		} else {
			try {
				User user = userService.searchUserWithEmail(newPasswordRequest.getEmail());
	
				String encodedPw = userService.encodePassword(newPasswordRequest.getPassword());
	
				user.setPassword(encodedPw);
				
				user = userService.editUser(user);
				
				ResponseEntity.ok(mrp);
			} catch (UserServiceException use) {
				LOGGER.error(use.getMessage());
				return null;
			}
		}

		return ResponseEntity.ok(mrp);
	}

}
