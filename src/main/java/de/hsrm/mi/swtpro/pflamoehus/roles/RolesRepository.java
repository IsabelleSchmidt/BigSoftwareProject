package de.hsrm.mi.swtpro.pflamoehus.roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * RolesRepository to make different operations in the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {
    
    Optional<Roles> findByName(ERoles name);

}
