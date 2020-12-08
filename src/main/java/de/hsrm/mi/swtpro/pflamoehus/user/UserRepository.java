package de.hsrm.mi.swtpro.pflamoehus.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);
    Optional <User> findById (long id);

    
}