package de.hsrm.mi.swtpro.pflamoehus.userapi;

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
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailService;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserMessage;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian, Sarah Wenzel
 * @version 6
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
     * @param newUser user that wants to get registerd
     * @param result shows errors if the given attributes are incorrect
     * @return user
     */

    @PostMapping("/user/new")
    public UserMessage registerUser(@Valid @RequestBody User newUser, BindingResult result) {

        UserMessage um = new UserMessage();
        userRestApiLogger.info("Neuen Benutzer erhalten");

        if (result.hasErrors()) {
            userRestApiLogger.info("Validierungsfehler" +  result.getFieldErrors().toString());
            um.setMessage("Validierungsfehler --" + result.getFieldErrors().toString());
        }

        else {
            try {
                newUser = userService.registerUser(newUser);
                um.setEmail(newUser.getEmail());
            } catch (EmailAlreadyInUseException aliu) {
                userRestApiLogger.error("User konnte nicht registriert werden.");
                um.setMessage("User konnte nicht registriert werden. Die Email-Adresse existiert bereits");
            }
        }

        return um;
    }

    /**
     * Login an already existing user.
     * 
     * @param loginUser user that wants to be logged in
     * @return user
     */
    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMessage loginUser(@RequestBody User loginUser) {

        userRestApiLogger.info("User wird versucht einzuloggen.");
        UserMessage um = new UserMessage();
       
        try{
            UserDetails ud = userdetailservice.loadUserByUsername(loginUser.getEmail());
            um.setEmail(loginUser.getEmail());
            
            if(pe.matches(loginUser.getPassword(), ud.getPassword())){
                userRestApiLogger.info("EINGELOGGT!");
                //um.setMessage("Einloggen erfolgreich.");
            }else{
                userRestApiLogger.error("Passwort ist falsch.");
                um.setMessage("Das angegebene Passwort ist falsch.");
                // throw new UserApiException("Das angegebene Passwort ist falsch.");
            }
            
        }catch(UsernameNotFoundException unfe){
            userRestApiLogger.error("User not found.");
            um.setMessage("Die angegebene Email-Adresse konnte nicht gefunden werden.");
            // throw new UserApiException("Die angegebene Email-Adresse konnte nicht gefunden werden.");
        }
        
        userRestApiLogger.info("__________RETURN__________");
        return um;
        
    }

//     @ExceptionHandler(value=UserApiException.class)
//     public void handleException(
//     UserApiException uae, HttpServletResponse response) throws IOException {
 
//         response.sendError(HttpStatus.BAD_REQUEST.value());
//         userRestApiLogger.info("RESPONSE: " + );
//         response.addHeader("LoginFehler", HttpStatus.BAD_REQUEST.getReasonPhrase());
 
//   }

}
