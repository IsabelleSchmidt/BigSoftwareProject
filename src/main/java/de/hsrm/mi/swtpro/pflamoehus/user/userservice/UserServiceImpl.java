package de.hsrm.mi.swtpro.pflamoehus.user.userservice;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.EmailAlreadyInUseException;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.UserServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

/*
 * UserServiceImpl for implementing the interface 'UserService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 4
 */
@Service
public class UserServiceImpl implements UserService{

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
    public User searchUserWithEmail(String email) { 

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
     * Edits and saves the given (new) user.
     * 
     * @param editedUser new or edited user
     * @return user
     */
    @Override
    public User editUser(User editedUser) {
        

        User foundUser = searchUserWithEmail(editedUser.getEmail());
        
        if (pe.matches(editedUser.getPassword(), foundUser.getPassword())) {
            encodePassword(editedUser.getPassword(), foundUser);
        }else{
            throw new UserServiceException("The new password does not match the old one.");
        }

        editBankcard(editedUser, foundUser);
        editCreditcard(editedUser, foundUser);
        foundUser.setFirstName(editedUser.getFirstName());
        foundUser.setLastName(editedUser.getLastName());
        foundUser.setGender(editedUser.getGender());
        foundUser.setBirthdate(editedUser.getBirthdate());
        
        //check whether new mail already has a password
        try{
            searchUserWithEmail(editedUser.getEmail());
        }catch(UserServiceException use){
            foundUser.setEmail(editedUser.getEmail());
        }
        
        return foundUser;

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

        user = userRepository.save(user);

        try {

            encodePassword(user.getPassword(), user);

            if (user.getBankcard() != null) {
                encodeIBAN(user.getBankcard() , user);
            }
            if (user.getCreditcard() != null) {
                encodeCardNumber(user.getCreditcard(), user);
            }

        } catch (OptimisticLockException ole) {

            throw new UserServiceException("User could not be saved into the database.");

        }

        userServiceLogger.info("User was saved into the repository.");
        return user;

    }

    /**
     * When creating a new user or editing the iban, the new iban has to get
     * encoded.
     * 
     * @param cards new or edited bankcards
     * @param user  user from database
     */

    private void encodeIBAN(List<Bankcard> cards, User user) {
        
        //checks whether user exists in database
        user = searchUserWithEmail(user.getEmail());
        if(cards.isEmpty()){
            throw new UserServiceException("No bankcard available for encoding");
        }

        
        for (Bankcard card : cards) {
            card.setIban(pe.encode(card.getIban()));
        
        }
        user.setBankcard(cards);
        userServiceLogger.info("all bankcards encoded.");
    }

    /**
     * When creating a new user or editing the creditcard number, the new number has
     * to get encoded.
     * 
     * @param cards edited or new creditcards
     * @param user  user from database
     */

    private void encodeCardNumber(List<Creditcard> cards, User user) {
       
        user = searchUserWithEmail(user.getEmail());
        
        if(cards.isEmpty()){
            throw new UserServiceException("No creditcard for encoding available");
        }
       
       
        for (Creditcard card : cards) {
            card.setCreditcardnumber(pe.encode(card.getCreditcardnumber()));
        }
        
            user.setCreditcard(cards);
            userServiceLogger.info("all creditcards encoded.");
          
            
        
    }

    /**
     * Creating a new user or editing the password requires encoding the attribute.
     * 
     * @param password new password
     * @param user     user from database or a new user
     */

    private void encodePassword(String password, User user) {

       user = searchUserWithEmail(user.getEmail());
        user.setPassword(pe.encode(password));
    }


    /**
     * When a user changes his bankcards, for example the iban or he adds another
     * one, we have to encode the new iban.
     * 
     * @param editedUser new user
     * @param foundUser  user from database
     */
    private void editBankcard(User editedUser, User foundUser) {
       
        List<Bankcard> newcards = editedUser.getBankcard();
        
        for (Bankcard newcard : newcards) {

            //If bankcard wasn't encoded before: encode
            if(!Pattern.matches("^\\{bcrypt\\}.*",newcard.getIban())){
                newcard.setIban(pe.encode(newcard.getIban()));
            }
            
        }
        foundUser.setBankcard(newcards);
    }

    /**
     * When a user changes his creditcards, for example the creditcard number or he
     * adds antoher one, we habe to encode the new creditcard number.
     * 
     * @param editedUser new user
     * @param foundUser  user from database
     */
    private void editCreditcard(User editedUser, User foundUser) {

      

        List<Creditcard> newcards = editedUser.getCreditcard();
        
        for (Creditcard newcard : newcards) {

            //If creditcard wasn't encoded before: encode
            if(!Pattern.matches("^\\{bcrypt\\}.*",newcard.getCreditcardnumber())){
                newcard.setCreditcardnumber(pe.encode(newcard.getCreditcardnumber()));
            }
            
        }
        foundUser.setCreditcard(newcards);
    }

}
