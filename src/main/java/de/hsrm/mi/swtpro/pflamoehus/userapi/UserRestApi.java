package de.hsrm.mi.swtpro.pflamoehus.userapi;

import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/user")
    public User registerUser(@RequestBody User user) throws UserApiException {
        try {
            user = userService.registerUser(user);
        } catch (EmailAlreadyInUseException aliu) {
            throw new UserApiException("Email already in use. Choose another one or log in.");
        }

        return user;
    }

    /**
     * Login an already existing user.
     * 
     * @param email user, that wants to get logged in
     * @return user
     */
    @GetMapping(value = "/user/{email}")  
    public User getUser(@PathVariable String email) {
        if (userService.searchUserWithEmail(email) != null) {
            return userService.searchUserWithEmail(email);
        } else {
            throw new UserApiException("This email doesn't exist. Register first.");
        }
    }
}
