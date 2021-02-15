package de.hsrm.mi.swtpro.pflamoehus.user.adress.adressservice;

import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.AdressServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;

@Service
public class AdressServiceImpl implements AdressService {

    @Autowired
    AdressRepository adressRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdressServiceImpl.class);

    
    /** 
     * Find adress by id.
     * 
     * @param id to be found
     * @return Adress
     */
    @Override
    @Transactional
    public Optional<Adress> findById(long id) {

        Optional<Adress> adress = adressRepo.findById(id);
        if (adress.isEmpty()) {
            throw new AdressServiceException();
        }

        return adress;

    }

    
    /** 
     * Find a adress by its city.
     * 
     * @param city to be found
     * @return all adresses
     */
    @Override
    public List<Adress> findByCity(String city) {
        return adressRepo.findByCity(city);
    }

    
    /** 
     * Find adresses by postcode.
     * 
     * @param postCode to be found
     * @return list of adresses
     */
    @Override
    public List<Adress> findPostCode(String postCode) {
        return adressRepo.findByPostCode(postCode);
    }

    
    /** 
     * Save adress in repository.
     * 
     * @param adress to be saved
     * @return Adress
     */
    @Override
    @Transactional
    public Adress saveAdress(Adress adress) {
        try{
            adress = adressRepo.save(adress);
        }catch(OptimisticLockException oLE){
            LOGGER.error("Adress can only be edited by one person at a time.");
            throw new AdressServiceException();
        }
        return adress;
    }

    
    /** 
     * Find all adresses.
     * 
     * @return all adresses
     */
    @Override
    public List<Adress> findAll() {
        return adressRepo.findAll();
    }

    
    /** 
     * Delete a adress.
     * 
     * @param id to be deleted
     */
    @Override
    @Transactional
    public void deleteAdress(long id) {
        Optional<Adress> a = findById(id);
        if (a.isPresent()){
            adressRepo.delete(a.get());
        }
        
    }

}
