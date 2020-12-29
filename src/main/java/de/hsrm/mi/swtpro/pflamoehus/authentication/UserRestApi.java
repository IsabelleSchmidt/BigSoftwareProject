package de.hsrm.mi.swtpro.pflamoehus.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request.LoginRequest;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request.SignUpRequest;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response.MessageResponse;
import de.hsrm.mi.swtpro.pflamoehus.roles.RolesRepository;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtUtils;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian, Sarah Wenzel
 * @version 7
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserRestApi {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(UserRestApi.class);

	/**
	 * @param loginRequest login values
	 * @param result bindingresult
	 * @return ResponseEntity
	 */
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
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
	 * @param result for controlling if the given SignUp request is valid
	 * @return ResponseEntity
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
		MessageResponse mr = new MessageResponse();
		logger.info("Benutzer wird registriert." + signUpRequest.toString());
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			mr.setMessage("Email ist already taken.");
			mr.setType("NOTVALID");
			return ResponseEntity.badRequest().body(mr);
		}

		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setBirthdate(signUpRequest.getBirthdate());
		user.setGender(signUpRequest.getGender());
		user.setPassword(signUpRequest.getPassword());

		List<String> strRoles = signUpRequest.getRole();
		List<Roles> roles = new ArrayList<>();

		logger.info("DAS KOMMT IN DER REQUEST MIT: " + signUpRequest.getRole());

		if (strRoles == null) {
			logger.info("ROLE IST LEER");
			logger.info("DAS HIER IST DIE USER ROLE: " + rolesRepository.findByName(ERoles.USER));
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
		logger.info("User wird gespeichert: " + user.toString());
		userRepository.save(user);

		mr.setMessage("User registered successfully!");
		return ResponseEntity.ok(mr);

	}
}
