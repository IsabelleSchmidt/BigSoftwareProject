package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUse;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserApiException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder pe;
    Logger userServiceLogger = LoggerFactory.getLogger(UserServiceImpl.class);

    
    /** 
     * @return List<User>
     */
    @Override
    public List<User> allUsers() {
        userServiceLogger.info("All Users requested.");
        return userRepository.findAll();
    }

    
    /** 
     * @param email
     * @return User
     */
    @Override
    public User searchUserWithEmail(String email) {
        userServiceLogger.info("Searching for user with given mail.");
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            userServiceLogger.error("User not found");
            throw new UserApiException("User with this mail wasn't found in the database");
        }
        return user.get();
    }

    
    /** 
     * @param id
     * @return Optional<User>
     */
    @Override
    public User searchUserWithId(long id) {
        userServiceLogger.info("Searching for User with given ID.");
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){throw new UserApiException("User with this ID wasn't found in the database");}
        return user.get();
    }

    
    /** 
     * @param editedUser
     * @return User
     */
    @Override
    public User editUser(User editedUser) {
        try{
            editedUser = userRepository.save(editedUser);
        }catch(OptimisticLockException oLE){
            oLE.printStackTrace();
            throw new UserApiException("User could not be saved into the database.");
        }
        userServiceLogger.info("User was successfully edited.");
        return editedUser;
    }

    
    /** 
     * @param id
     */
    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            userServiceLogger.error("User was not deleted, userID not found");
            throw new UserApiException("User with given ID was not found in the database.");
        }else{
            userServiceLogger.info("User with given ID was deleted.");
            userRepository.delete(user.get());
        }

    }

    
    /** 
     * @param email
     * @param password
     * @return boolean
     */
    // @Override
    // public boolean checkLogin(String email, String password) {
    //     userServiceLogger.info("checking passwords");
    //     Optional<User> user = userRepository.findByEmail(email);
    //     if(user.isPresent()){
    //         if(user.get().getPassword().equals(password)){
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    
    /** 
     * @param user
     * @return User
     */
    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            userServiceLogger.error("invalid mail.");
            throw new EmailAlreadyInUse();
        }
        try{
            String password = userRepository.findByEmail(user.getEmail()).get().getPassword();
            pe.encode(password);
            userRepository.findByEmail(user.getEmail()).get().setPassowrd(password);
            userRepository.save(user);
        }catch(OptimisticLockException ole){
            ole.printStackTrace();
            userServiceLogger.error("Error while saving user into database");
            throw new UserApiException("User could not be saved into the database.");
            
        }
        userServiceLogger.info("User was saved into the repository.");
        return userRepository.save(user);
    }
    
}
