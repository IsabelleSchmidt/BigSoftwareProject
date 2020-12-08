package de.hsrm.mi.swtpro.pflamoehus.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);
    Optional <User> findById (long id);
    List<User> findByAdress (Adress adress);
    
}