package de.hsrm.mi.swtpro.pflamoehus.user.adress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * AdressRepository for different operations to apply on the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
 public interface AdressRepository extends JpaRepository<Adress, Long> {

    /**
     * Find all adresses in the given city.
     * 
     * @param city wanted city
     * @return list of adresses
     */
    List<Adress> findByCity (String city);

    /**
     * Find all adresses with the given postcode.
     * 
     * @param postCode wanted postcode
     * @return list of adresses
     */
    List<Adress> findByPostCode (String postCode);

    /**
     * Find a adress with the given id.
     * 
     * @param id wanted id
     * @return optional of type adress
     */
    Optional<Adress> findById (long id);

}
