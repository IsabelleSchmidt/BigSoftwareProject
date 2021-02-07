package de.hsrm.mi.swtpro.pflamoehus.user.userservice;

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
     * Saves an edited User into the database.
     * 
     * @param user to be saved
     * @return saved user
     */
    User editUser(User user);

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

    /**
     * Shows if the user with this email exits in the database
     * 
     * @param email searched email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * searches for an existing User and initializes all related Lazy Collections 
     * @param email the email of the user who is to be found and initialized
     * @return the user with initialized lazy collections
     */
    User getFullyInitializedUser(String email);

    /**
     * Encodes the Password
     * @param password password to encode
     * @return encoded password
     */
    String encodePassword(String password);

}
