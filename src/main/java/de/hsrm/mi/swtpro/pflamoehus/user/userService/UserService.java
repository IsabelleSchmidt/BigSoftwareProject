package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * UserService for different operations to apply on the products.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface UserService {

    /**
     * Gets a list of all users found in the database.
     * 
     * @return list of users
     */
    List<User> allUsers();

    /**
     * Searches the user with the given email adress.
     * 
     * @param email wanted email
     * @return user
     */
    User searchUserWithEmail(String email);

    /**
     * Searches the user with the given id.
     * 
     * @param id wanted id
     * @return user
     */
    User searchUserWithId(long id);

    /**
     * Edits and saves the given (new) user.
     * 
     * @param editedUser editet user that has to be saved
     * @return user
     */
    User editUser(User editedUser);

    /**
     * Deletes the user with the given id.
     * 
     * @param id user id that has to be deleted
     */
    void deleteUser(long id);

    /**
     * Registers the new user given.
     * 
     * @param email user that has to get registered
     * @return user
     */
    User registerUser(User email);

}
