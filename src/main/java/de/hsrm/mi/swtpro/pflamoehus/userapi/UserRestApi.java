package de.hsrm.mi.swtpro.pflamoehus.userapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.userService.UserService;

@RestController
@RequestMapping("/api")
public class UserRestApi {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public User registerUser(@RequestBody User user) throws UserApiException {
        try {
            user = userService.registerUser(user);
        } catch (EmailAlreadyInUseException aliu) {
            throw new UserApiException("Email already in use. Choose another one or log in.");
        }

        return user;
    }

    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)   //TODO: Anny -> anschauen, was MediaType etc. genau nochmal tut
    public User getUser(@PathVariable("email") String email) {
        if (userService.searchUserWithEmail(email) != null) {
            return userService.searchUserWithEmail(email);
        } else {
            throw new UserApiException("This email doesn't exist. Register first.");
        }
    }
}
