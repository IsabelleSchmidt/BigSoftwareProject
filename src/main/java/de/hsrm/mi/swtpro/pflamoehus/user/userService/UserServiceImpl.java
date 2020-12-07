package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUse;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired UserRepository userRepository;
    Logger userServiceLogger = LoggerFactory.getLogger(UserServiceImpl.class);

    
    /** 
     * @return List<User>
     */
    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    
    /** 
     * @param email
     * @return User
     */
    @Override
    public User searchUserWithEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
    /** 
     * @param id
     * @return Optional<User>
     */
    @Override
    public Optional<User> searchUserWithId(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isEmpty() ? null : user;
    }

    
    /** 
     * @param editedUser
     * @return User
     */
    @Override
    public User editUser(User editedUser) {
        try{
            userRepository.save(editedUser);
        }catch(OptimisticLockException oLE){
            oLE.printStackTrace();
        }
        return editedUser;
    }

    
    /** 
     * @param id
     */
    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            userServiceLogger.info("User was not deleted, userID not founf");
        }else{
            userRepository.delete(user.get());
        }

    }

    
    /** 
     * @param email
     * @param password
     * @return boolean
     */
    @Override
    public boolean checkLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    
    /** 
     * @param user
     * @return User
     */
    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyInUse();
        }
        userRepository.save(user);
        return user;
    }
    
}
