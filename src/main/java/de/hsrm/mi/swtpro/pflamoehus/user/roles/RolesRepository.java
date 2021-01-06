package de.hsrm.mi.swtpro.pflamoehus.user.roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * RolesRepository to make different operations in the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {
    
    /**
     * Find a role by its name.
     * 
     * @param name wanted name
     * @return role
     */
    Optional<Roles> findByName(ERoles name);

}
