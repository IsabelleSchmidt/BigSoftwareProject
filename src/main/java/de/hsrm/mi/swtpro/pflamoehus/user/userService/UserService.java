package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

public interface UserService {

    /**
     * @return a list of all users 
     */
    List<User> allUsers();

    /**
     * @param email
     * @return 
     */
    User searchUserWithEmail(String email);

    /**
     * @param id
     * @return 
     */
    Optional<User> searchUserWithId (long id);

    /**
     * saves the edited user in the database
     * @param editedUser user object that has been edited 
     * @return the edited, saved user if the saving process was successful, otherwise null
     */
    User editUser (User editedUser);

    /**
     * Deletes the user with the given id in the database
     */
    void deleteUser (long id);

    boolean checkLogin (String email, String password);

    User registerUser(User email);
    
}
    

