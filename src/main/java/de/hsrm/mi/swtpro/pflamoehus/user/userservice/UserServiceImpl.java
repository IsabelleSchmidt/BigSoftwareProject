package de.hsrm.mi.swtpro.pflamoehus.user.userservice;

import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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
    @Transactional
    public User searchUserWithEmail(String email) {
        LOGGER.info("SEARCH WITH MAIL");
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public User registerUser(User user) {
       
        Optional<User> found = userRepository.findByEmail(user.getEmail());

        if (found.isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        try{
          
            user.setPassword(encodePassword(user.getPassword()));
        }catch(OptimisticLockException ole){
            throw new UserServiceException("Password couldn't be changed.");
        }

        user = userRepository.save(user);
        
        LOGGER.info("User was saved into the repository.");
        return user;

    }

    /**
     * Creating a new user or editing the password requires encoding the attribute.
     * 
     * @param password new password
     * @param user     user from database or a new user
     */
    public String encodePassword(String password) {
       
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

    @Override
    @Transactional
    public User editUser(User user) {
        try {
            user = userRepository.save(user);
        } catch (OptimisticLockException oLE) {
            throw new UserServiceException();
        }
        return user;

    }
    @Transactional
    public User getFullyInitializedUser(String email){
       
        User user = searchUserWithEmail(email);
        if(!Hibernate.isInitialized(user.getAllAdresses())){
            Hibernate.initialize(user.getAllAdresses());
        }
        if(!Hibernate.isInitialized(user.getBankcard())){
            Hibernate.initialize(user.getBankcard());
        }
        if(!Hibernate.isInitialized(user.getCreditcard())){
            Hibernate.initialize(user.getCreditcard());
        }
        if(!Hibernate.isInitialized(user.getAllOrders())){
            Hibernate.initialize(user.getAllOrders());
        }
        

        LOGGER.info("User was initialized");
        return user;
    }

}
