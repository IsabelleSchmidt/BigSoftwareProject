package de.hsrm.mi.swtpro.pflamoehus.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
/*
 * User-Entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by its email.
     * 
     * @param email wanted email
     * @return optinal of type user
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by its id.
     * 
     * @param id wanted id
     * @return optional of type user
     */
    Optional<User> findById(long id);

}