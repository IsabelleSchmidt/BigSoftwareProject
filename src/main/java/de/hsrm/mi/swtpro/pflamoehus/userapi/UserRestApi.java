package de.hsrm.mi.swtpro.pflamoehus.userapi;

import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

/*
 * UserRestController for the communcation between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
@RestController
@RequestMapping("/api")
public class UserRestApi {

    @Autowired
    UserService userService;

    /**
     * Register a new given user.
     * 
     * @param user user that should get registered
     * @return user
     * @throws UserApiException gets thrown if the email is already in use
     */
    @PostMapping("/user/new")
    public String registerUser(@Valid @RequestBody User user, BindingResult result){
        String message = "message: ";
        if(result.hasErrors()){
            message+=" --bindingerror-- "+result.toString();
        }
        if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,32}$", user.getPassword())){
            message+=" --passwortfehler--- passwort braucht klein-, Großbuchstaben, Zahlen und Sonerzeichen.";
        }
        else{
             try {
            user = userService.registerUser(user);
        } catch (EmailAlreadyInUseException aliu) {
            throw new UserApiException("Email already in use. Choose another one or log in.");
        }
        }
       

        return user.toString()+message;
    }

    /**
     * Login an already existing user.
     * 
     * @param email user, that wants to get logged in
     * @return user
     */
    @GetMapping(value = "/user/get/{email}")  
    public User getUser(@PathVariable String email) {
        if (userService.searchUserWithEmail(email) != null) {
            return userService.searchUserWithEmail(email);
        } else {
            throw new UserApiException("This email doesn't exist. Register first.");
        }
    }
}
