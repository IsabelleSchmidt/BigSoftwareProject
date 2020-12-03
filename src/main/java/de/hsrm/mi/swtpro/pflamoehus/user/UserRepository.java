package de.hsrm.mi.swtpro.pflamoehus.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);
    User findByFirstName (String firstName);
    User findByLastName (String lastName);

    List<User> findByAdress (Adress adress);
    
    


}