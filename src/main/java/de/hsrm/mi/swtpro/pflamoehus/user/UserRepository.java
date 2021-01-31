package de.hsrm.mi.swtpro.pflamoehus.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
/*
 * User-Repository for its database.
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

    /**
     * Look up if the user with this email exits.
     * 
     * @param email wanted email
     * @return boolean
     */
    Boolean existsByEmail(String email);

    /**
     * Find a users by their roles.
     * 
     * @param role wanted role
     * @return list of users
     */
    List<User> findByRoles(Roles role);

}