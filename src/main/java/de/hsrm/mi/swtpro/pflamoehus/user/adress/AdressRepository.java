package de.hsrm.mi.swtpro.pflamoehus.user.adress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {

    List<Adress> findByCity (String City);
    List<Adress> findByPostcode (int postCode);
    Optional<Adress> findById (long id);

}
