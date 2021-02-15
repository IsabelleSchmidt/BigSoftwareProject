package de.hsrm.mi.swtpro.pflamoehus.user.adress.adressservice;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

public interface AdressService {

    /** 
     * Find adress by id.
     * 
     * @param id to be found
     * @return Adress
     */
    Optional<Adress> findById (long id);

    /** 
     * Find a adress by its city.
     * 
     * @param city to be found
     * @return all adresses
     */
    List<Adress> findByCity(String city);

    /** 
     * Find adresses by postcode.
     * 
     * @param postCode to be found
     * @return list of adresses
     */
    List<Adress> findPostCode(String postCode);

    /** 
     * Save adress in repository.
     * 
     * @param adress to be saved
     * @return Adress
     */
    Adress saveAdress (Adress adress);

    /** 
     * Find all adresses.
     * 
     * @return all adresses
     */
    List<Adress> findAll();

    /** 
     * Delete a adress.
     * 
     * @param id to be deleted
     */
    void deleteAdress(long id);
    
}
