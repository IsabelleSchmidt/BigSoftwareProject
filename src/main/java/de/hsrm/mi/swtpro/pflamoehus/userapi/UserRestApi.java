package de.hsrm.mi.swtpro.pflamoehus.userapi;

import java.util.regex.Pattern;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;
import de.hsrm.mi.swtpro.pflamoehus.validation.product_db.ValidRoomType;
import de.hsrm.mi.swtpro.pflamoehus.validation.product_db.ValidRoomTypeValidator;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidPassword;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 4
 */
@RestController
@RequestMapping("/api")
public class UserRestApi {

    @Autowired
    UserService userService;

    Logger userRestApiLogger = LoggerFactory.getLogger(UserRestApi.class);

    /**
     * Register a new given user.
     * 
     * @param user user that should get registered
     * @return user
     * @throws UserApiException gets thrown if the email is already in use
     */
 

    @PostMapping("/user/new")
    public String registerUser(@Valid @RequestBody User newUser, BindingResult result){
    
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
                    message+="register_error";
                    return message;
                 }
            }
        

        return newUser.toString() + message;
    }

    /**
     * Login an already existing user.
     * 
     * @param email user, that wants to get logged in
     * @return user
     */
    @GetMapping(value = "/user/get/{email}")
    public String getUser(@PathVariable("email") String email) {
        userRestApiLogger.info("User wird versucht einzuloggen.");
        User user = null;
        String message = "Message: ";

        if (userService.searchUserWithEmail(email) != null) {
            user = userService.searchUserWithEmail(email);
        } else {
            userRestApiLogger.error("Email Adresse konnte nicht gefunden werden.");
            message += "login_error";
        }

        return user.toString() + message;
    }

}
