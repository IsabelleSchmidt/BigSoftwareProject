package de.hsrm.mi.swtpro.pflamoehus.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    Benutzer findByName (String name);
    
}
