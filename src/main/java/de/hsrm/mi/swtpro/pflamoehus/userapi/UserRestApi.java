package de.hsrm.mi.swtpro.pflamoehus.userapi;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailService;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian, Sarah Wenzel
 * @version 5
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserRestApi {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailService userdetailservice;

    @Autowired
    PasswordEncoder pe;

    Logger userRestApiLogger = LoggerFactory.getLogger(UserRestApi.class);

    /**
     * Register a new given user.
     * 
     * @param user user that should get registered
     * @return user
     * @throws UserApiException gets thrown if the email is already in use
     */

    @PostMapping("/user/new")
    public String registerUser(@Valid @RequestBody User newUser, BindingResult result) {

        String message = "message: ";
        userRestApiLogger.info("Neuen Benutzer erhalten");

        if (result.hasErrors()) {
            userRestApiLogger.info("Validierungsfehler");
            message += "Validierungsfehler --" + result.getFieldErrors().toString();
        }

        else {
            try {
                newUser = userService.registerUser(newUser);
            } catch (EmailAlreadyInUseException aliu) {
                userRestApiLogger.error("User konnte nicht registriert werden.");
                message += "register_error";
                return message;
            }
        }

        return newUser.toString() + message;
    }

    /**
     * Login an already existing user.
     * 
     * @param email email of the user, that wants to get logged in
     * @param password password of the user, that wants to get logged in
     * @return user
     */
    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> loginUser(@RequestBody User loginUser) {
        userRestApiLogger.info("User wird versucht einzuloggen.");

        String message = "";

        try{
            UserDetails ud = userdetailservice.loadUserByUsername(loginUser.getEmail());
            
            if(pe.matches(loginUser.getPassword(), ud.getPassword())){
                message+= "Erfolgreich eingeloggt";
                userRestApiLogger.info("EINGELOGGT!");
            }else{
                userRestApiLogger.error("Passwort ist falsch.");
                message+= "Passwort ist falsch. ";
            }
            
        }catch(UsernameNotFoundException unfe){
            userRestApiLogger.error("User not found.");
            message+= "Die Email-Adresse existiert nicht. ";
        }

        userRestApiLogger.info(loginUser.toString() + message);
        userRestApiLogger.info("RESPONSE: " + Collections.singletonMap("response", message));
        return Collections.singletonMap("message", message);
        
    }

}
