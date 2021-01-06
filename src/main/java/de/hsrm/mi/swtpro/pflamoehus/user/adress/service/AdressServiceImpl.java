package de.hsrm.mi.swtpro.pflamoehus.user.adress.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.AdressServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;

@Service
public class AdressServiceImpl implements AdressService {

    @Autowired
    AdressRepository adressRepo;

    private static final Logger ADRESSSERVICE_LOGGER = LoggerFactory.getLogger(AdressServiceImpl.class);

    @Override
    public Optional<Adress> findById(long id) {

        Optional<Adress> adress = adressRepo.findById(id);
        if (adress.isEmpty()) {
            throw new AdressServiceException();
        }

        return adress;

    }

    @Override
    public List<Adress> findByCity(String city) {
        return adressRepo.findByCity(city);
    }

    @Override
    public List<Adress> findPostCode(String postCode) {
        return adressRepo.findByPostCode(postCode);
    }

    @Override
    public Adress saveAdress(Adress adress) {
        try{
            adress = adressRepo.save(adress);
        }catch(OptimisticLockException oLE){
            ADRESSSERVICE_LOGGER.error("Adress can only be edited by one person at a time.");
            throw new AdressServiceException();
        }
        return adress;
    }

    @Override
    public List<Adress> findAll() {
        return adressRepo.findAll();
    }

    @Override
    public void deleteAdress(long id) {
        Optional<Adress> a = findById(id);
        adressRepo.delete(a.get());
    }

}
