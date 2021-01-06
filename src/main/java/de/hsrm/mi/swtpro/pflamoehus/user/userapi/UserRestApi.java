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
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request.SignUpRequest;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request.UserOrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response.MessageResponse;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.AdressServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.BankcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.*;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.service.*;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service.*;

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
	UserRepository userRepository;

	@Autowired
	RolesRepository rolesRepository;

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
	JwtUtils jwtUtils;

	private static final Logger LOGGER2 = LoggerFactory.getLogger(UserRestApi.class);

	/**
	 * PostMapping for login.
	 * 
	 * @param result       binding result
	 * @param loginRequest login values
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
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
	 * @param signUpRequest given values of the user that wants to be registerd
	 * @param result        for controlling if the given SignUp request is valid
	 * @return ResponseEntity
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
		MessageResponse mr = new MessageResponse();
		List<MessageResponse> mrs = new ArrayList<>();
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			mr.setMessage("Email ist already taken.");
			mr.setField("email");
			mrs.add(mr);
			return new ResponseEntity<>(mrs, HttpStatus.OK);
		}
		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				mr.setMessage(error.getDefaultMessage());
				mr.setField(error.getField());
				mrs.add(mr);
				LOGGER2.info("FEHLER: " + mr);
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
			Roles userRole = rolesRepository.findByName(ERoles.USER)

					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":

						Roles adminRole = rolesRepository.findByName(ERoles.ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "staff":
						Roles modRole = rolesRepository.findByName(ERoles.STAFF)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					case "service":
						Roles serviceRole = rolesRepository.findByName(ERoles.SERVICE)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(serviceRole);

						break;
					case "warehouse":
						Roles warehouseRole = rolesRepository.findByName(ERoles.WAREHOUSE)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(warehouseRole);

						break;
					default:
						Roles userRole = rolesRepository.findByName(ERoles.USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);

		mr.setMessage("User registered successfully!");
		return ResponseEntity.ok(mr);

	}

	@PostMapping("/addInfos")
	public ResponseEntity<?> addInfosToUser(@Valid @RequestBody UserOrderRequest userOrderRequest, BindingResult result)
			throws AuthenticationException {

		MessageResponse mr = new MessageResponse();
		List<MessageResponse> mrs = new ArrayList<>();

		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				mr.setMessage(error.getDefaultMessage());
				mr.setField(error.getField());
				mrs.add(mr);
				LOGGER2.info("FEHLER: " + mr);
			}

			return new ResponseEntity<>(mrs, HttpStatus.OK);
		}
		// TODO: eventuell Validation-Methoden
		Adress newAdress = userOrderRequest.getAdress();
		Bankcard newBankcard = userOrderRequest.getBankcard();
		Creditcard newCreditcard = userOrderRequest.getCreditcard();

		String jwtToken = userOrderRequest.getToken();
		String email = jwtUtils.getUserNameFromJwtToken(jwtToken);

		try {

			User user = userService.searchUserWithEmail(email);

			if (newAdress != null) {
				adressSerivce.saveAdress(newAdress);
				user.getAllAdresses().add(newAdress);
			}

			if (newBankcard != null) {
				bankcardSerivce.saveBankcard(newBankcard);
				user.getBankcard().add(newBankcard);
			}

			if (newCreditcard != null) {
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
		mr.setMessage("Neue Daten erfolgreich hinzugefügt.");
		return ResponseEntity.ok(mr);
	}

}
