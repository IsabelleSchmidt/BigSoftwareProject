package de.hsrm.mi.swtpro.pflamoehus.user.userservice;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

/*
 * UserServiceImpl for implementing the interface 'UserService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 4
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pe;

    Logger userServiceLogger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Returns a list of all users written in the database.
     * 
     * @return list of users
     */
    @Override
    public List<User> allUsers() { // gut so
        return userRepository.findAll();
    }

    /**
     * Returns the user with the given email adress.
     * 
     * @param email wanted email
     * @return user
     */
    @Override
    public User searchUserWithEmail(String email) throws UserServiceException {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserServiceException("User with this mail wasn't found in the database");
        }
        return user.get();
    }

    /**
     * Searches for the user with the given id.
     * 
     * @param id wanted id
     * @return user
     */
    @Override
    public User searchUserWithId(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserServiceException("User with this ID wasn't found in the database");
        }
        return user.get();
    }

    /**
     * Delets the user with the given id.
     * 
     * @param id user id that should get deleted
     */
    @Override
    public void deleteUser(long id) { // gut so
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserServiceException("User with given ID was not found in the database.");
        } else {
            userRepository.delete(user.get());
        }

    }

    /**
     * Registers the new user given.
     * 
     * @param user new user
     * @return user
     */
    @Override
    public User registerUser(User user) {
        Optional<User> found = userRepository.findByEmail(user.getEmail());

        if (found.isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        try {

            user.setPassword(encodePassword(user.getPassword()));

        } catch (OptimisticLockException ole) {

            throw new UserServiceException("User could not be saved into the database.");

        }
        user = userRepository.save(user);
        userServiceLogger.info("User was saved into the repository.");
        return user;

    }

    /**
     * Creating a new user or editing the password requires encoding the attribute.
     * 
     * @param password new password
     * @param user     user from database or a new user
     */
    private String encodePassword(String password) {

        return pe.encode(password);
    }

    /**
     * Shows if the user with this email exits in the database
     * 
     * @param email searched email
     * @return boolean
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    

}
