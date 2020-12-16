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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder pe;
    Logger userServiceLogger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @return List<User>
     * 
     *         returns all users in the database
     */
    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /**
     * @param email
     * @return User
     * 
     *         returns the user with the given email
     */
    @Override
    public User searchUserWithEmail(String email) {
        userServiceLogger.info("Searching for user with given mail.");
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            userServiceLogger.error("User not found");
            throw new UserServiceException("User with this mail wasn't found in the database");
        }
        return user.get();
    }

    /**
     * @param id
     * @return Optional<User>
     * 
     *         return the user with the given id
     */
    @Override
    public User searchUserWithId(long id) {
        userServiceLogger.info("Searching for User with given ID.");
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserServiceException("User with this ID wasn't found in the database");
        }
        return user.get();
    }

    /**
     * @param editedUser
     * @return User
     * 
     *         editing or creating a new user
     */
    @Override
    public User editUser(User editedUser) {
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
     * @param id
     * 
     *           deleting a user with the given id
     */
    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserServiceException("User with given ID was not found in the database.");
        } else {
            userRepository.delete(user.get());
        }

    }

    /**
     * @param user
     * @return User
     * 
     *         register a new user
     */
    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }
        try {
            encodePassword(userRepository.findByEmail(user.getEmail()).get().getPassword(), user);
            encodeIBAN(userRepository.findByEmail(user.getEmail()).get().getBankcard(), user);
            encodeCardNumber(userRepository.findByEmail(user.getEmail()).get().getCreditcard(), user);
        } catch (OptimisticLockException ole) {
            ole.printStackTrace();
            throw new UserServiceException("User could not be saved into the database.");

        }
        return userRepository.save(user);
    }

    /**
     * 
     * 
     * @param cards
     * @param user
     * 
     *              Creating a new user oder editing the iban requires encoding the
     *              attribute
     */
    private void encodeIBAN(List<Bankcard> cards, User user) {
        for (Bankcard card : cards) {
            pe.encode(card.getIban());
        }
        userRepository.findByEmail(user.getEmail()).get().setBankcard(cards);
        userRepository.save(user);
    }

    /**
     * 
     * @param cards
     * @param user
     * 
     *              Creating a new user oder editing the cardnumber requires
     *              encoding the attribute
     */
    private void encodeCardNumber(List<Creditcard> cards, User user) {
        for (Creditcard card : cards) {
            pe.encode(card.getCreditcardnumber());
        }
        userRepository.findByEmail(user.getEmail()).get().setCreditcard(cards);
        userRepository.save(user);
    }

    /**
     * @param password
     * @param user
     * 
     *                 Creating a new user oder editing the password requires
     *                 encoding the attribute
     */
    private void encodePassword(String password, User user) {
        pe.encode(password);
        userRepository.findByEmail(user.getEmail()).get().setPassword(password);
        userRepository.save(user);
    }

    /**
     * @param editedUser
     * @param foundUser
     * 
     *                   If a user changes his Bankcards, for Example the IBAN or he
     *                   adds another one, we have to encode the new IBAN number
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
     * @param editedUser
     * @param foundUser
     * 
     *                   If a user changes his creditcards, for example the
     *                   credicartnumber or he adds another one, we have to encode
     *                   the new creditcardnumber
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
