package de.hsrm.mi.swtpro.pflamoehus.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName (String name);
    
}
