package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;
import java.util.Optional;

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
 * @version 3
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
    public List<User> allUsers() {  //gut so
        return userRepository.findAll();
    }

    /**
     * Returns the user with the given email adress.
     * 
     * @param email wanted email
     * @return user
     */
    @Override
    public User searchUserWithEmail(String email) { //gut so
            
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
    public User searchUserWithId(long id) { //gut so
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
    public User editUser(User editedUser) { //TODO nochmal drueber schauen
        try {
            // TODO: überprüfen ob immer gleich encodet wird
            User foundUser = searchUserWithEmail(editedUser.getEmail());
            if (!pe.encode(editedUser.getPassword()).equals(foundUser.getPassword())) {
                encodePassword(editedUser.getPassword(), editedUser);
            }
            editBankcard(editedUser, foundUser);
            editCreditcard(editedUser, foundUser);

        } catch (OptimisticLockException oLE) {
            oLE.printStackTrace();
            throw new UserServiceException("User could not be saved into the database.");
        }

        return editedUser;

    }

    /**
     * Delets the user with the given id.
     * 
     * @param id user id that should get deleted
     */
    @Override
    public void deleteUser(long id) {   //gut so
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

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        
        user = userRepository.save(user);

        try{
            //TODO: encoder faellt durch unsere pwd validierung, nur einmal beim erstellen validieren
            //encodePassword(user.getPassword(), user);

            if(user.getBankcard()!=null ){
                encodeIBAN(userRepository.findByEmail(user.getEmail()).get().getBankcard(), user);
            }
            if(user.getCreditcard()!=null){
                 encodeCardNumber(userRepository.findByEmail(user.getEmail()).get().getCreditcard(), user);
            }
           
        }catch(OptimisticLockException ole){

            ole.printStackTrace();
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
     * @param user user from database
     */
   
    private void encodeIBAN(List<Bankcard> cards, User user) {
        for (Bankcard card : cards) {

            pe.encode(card.getIban());
        }
        userRepository.findByEmail(user.getEmail()).get().setBankcard(cards);
        userRepository.save(user);
    }

    /**
     * When creating a new user or editing the creditcard number, the new number has
     * to get encoded.
     * 
     * @param cards edited or new creditcards
     * @param user user from database
     */

    private void encodeCardNumber (List<Creditcard> cards, User user){
        String encodedNr;
        for(Creditcard card: cards){
            encodedNr = pe.encode(card.getCreditcardnumber());
            card.setCreditcardnumber(encodedNr);

        }
        user = userRepository.findByEmail(user.getEmail()).get();
        user.setCreditcard(cards);
        userRepository.save(user);
    }

    /**
     * Creating a new user or editing the password requires encoding the attribute.
     * 
     * @param password new password
     * @param user user from database or a new user
     */

    private void encodePassword (String password, User user){
        password = pe.encode(password);
        user = userRepository.findByEmail(user.getEmail()).get();
        user.setPassword(password);
        userRepository.save(user);
    }

    /**
     * When a user changes his bankcards, for example the iban or he adds another
     * one, we have to encode the new iban.
     * 
     * @param editedUser new user
     * @param foundUser user from database
     */
    private void editBankcard(User editedUser, User foundUser) {

        if (editedUser.getBankcard().size() == foundUser.getBankcard().size()) {
            for (Bankcard card1 : editedUser.getBankcard()) {
                for (Bankcard card2 : foundUser.getBankcard()) {
                    if (!pe.encode(card1.getIban()).equals(card2.getIban())) {
                        card1.setIban(pe.encode(card1.getIban()));
                    }
                }
            }
        } else if (editedUser.getBankcard().size() != foundUser.getBankcard().size()) {
            editedUser.getBankcard().get(editedUser.getBankcard().size() - 1)
                    .setIban(pe.encode(editedUser.getBankcard().get(editedUser.getBankcard().size() - 1).getIban()));
        }
    }

    /**
     * When a user changes his creditcards, for example the creditcard number or he
     * adds antoher one, we habe to encode the new creditcard number.
     * 
     * @param editedUser new user
     * @param foundUser user from database
     */
    private void editCreditcard(User editedUser, User foundUser) {

        if (editedUser.getCreditcard().size() == foundUser.getCreditcard().size()) {
            for (Creditcard card1 : editedUser.getCreditcard()) {
                for (Creditcard card2 : foundUser.getCreditcard()) {
                    if (!pe.encode(card1.getCreditcardnumber()).equals(card2.getCreditcardnumber())) {
                        card1.setCreditcardnumber(pe.encode(card1.getCreditcardnumber()));
                    }
                }
            }
        } else if (editedUser.getCreditcard().size() != foundUser.getCreditcard().size()) {
            editedUser.getCreditcard().get(editedUser.getBankcard().size() - 1).setCreditcardnumber(
                    pe.encode(editedUser.getBankcard().get(editedUser.getBankcard().size() - 1).getIban()));
        }

    }

}
