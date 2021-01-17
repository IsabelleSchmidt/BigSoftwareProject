package de.hsrm.mi.swtpro.pflamoehus.user.userapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
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
import de.hsrm.mi.swtpro.pflamoehus.payload.request.SignUpRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.UserOrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.MessageResponse;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.AdressServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.BankcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.api.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailServiceImpl;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.*;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.service.*;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service.*;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.service.RoleService;

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
	UserDetailServiceImpl uds;

	private static final Logger LOGGER2 = LoggerFactory.getLogger(UserRestApi.class);

	/**
	 * PostMapping for login.
	 * 
	 * @param result       binding result
	 * @param loginRequest login values
	 * @return ResponseEntity
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
				
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

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
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
		LOGGER2.info("REGISTRIERE.");
		MessageResponse mr = new MessageResponse();
		List<MessageResponse> mrs = new ArrayList<>();
		if (userService.existsByEmail(signUpRequest.getEmail())) {
			mr.setMessage("Email ist already taken.");
			mr.setField("email");
			mrs.add(mr);
			LOGGER2.error("EMAIL IS ALREADY TAKEN.");
			return new ResponseEntity<>(mrs, HttpStatus.OK);
		}
		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				MessageResponse mrp = new MessageResponse();
				mrp.setMessage(error.getDefaultMessage());
				mrp.setField(error.getField());
				mrs.add(mrp);
				LOGGER2.info("FEHLER: " + mrp);

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

		LOGGER2.info("PASSWORT REGISTER VERSCHL: " + encoder.encode(signUpRequest.getPassword()));

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

		return ResponseEntity.ok(mr);

	}

	/**
	 * PostMapping for the data the user has to give while he is ordering.
	 * 
	 * @param userOrderRequest includes a token, paymentmethod and adress
	 * @param result           for showing mistakes in the formular
	 * @return response
	 * @throws AuthenticationException if the user is not known in the database
	 */
	@PostMapping("/newOrder/user")
	public ResponseEntity<?> addInfosToUser(@Valid @RequestBody UserOrderRequest userOrderRequest, BindingResult result)
			throws AuthenticationException {
		MessageResponse mr = new MessageResponse();
		List<MessageResponse> mrs = new ArrayList<>();

		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				MessageResponse mrp = new MessageResponse();
				mrp.setMessage(error.getDefaultMessage());
				mrp.setField(error.getField());
				mrs.add(mrp);
				LOGGER2.info("ERROR: " + mrp);
			}
			if (mrs.size() > 0) {
				return new ResponseEntity<>(mrs, HttpStatus.OK);
			}

		}

		JwtResponse jwtToken = userOrderRequest.getToken();
		String email = jwtToken.getEmail();

		try {
			User user = userService.searchUserWithEmail(email);

			if (userOrderRequest.getAdress() != null) {
				Adress newAdress = userOrderRequest.getAdress();
				adressSerivce.saveAdress(newAdress);
				user.getAllAdresses().add(newAdress);
			}

			if (userOrderRequest.getBankCard() != null) {
				Bankcard newBankcard = userOrderRequest.getBankCard();
				bankcardSerivce.saveBankcard(newBankcard);
				user.getBankcard().add(newBankcard);
			}

			if (userOrderRequest.getCreditcard() != null) {
				Creditcard newCreditcard = new Creditcard();
				creditcardService.saveCreditcard(newCreditcard);
				user.getCreditcard().add(newCreditcard);
			}

		} catch (AdressServiceException ase) {
			LOGGER2.error("Adress couldn't be saved.");
			throw new UserApiException("Adress couldn't be saved.");

		} catch (BankcardServiceException bse) {
			LOGGER2.error("Bankcard couldn't be saved.");
			throw new UserApiException("Adress couldn't be saved.");

		} catch (CreditcardServiceException cse) {
			LOGGER2.error("Creditcard couldn't be saved.");
			throw new UserApiException("Creditcard couldn't be saved.");

		} catch (UserServiceException use) {
			throw new AuthenticationException();
		}
		return ResponseEntity.ok(mr);
	}

	@GetMapping("/email/{email}")
	public User getUserWithMail(@PathVariable String email){
		User user;
		try{
			user = userService.searchUserWithEmail(email);
		}catch(UserServiceException use){
			LOGGER2.info(use.getMessage());
			return null;
		}
		return user;
		
	}

}
