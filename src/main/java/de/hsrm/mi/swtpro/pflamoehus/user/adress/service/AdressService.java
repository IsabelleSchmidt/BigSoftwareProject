package de.hsrm.mi.swtpro.pflamoehus.user.adress.service;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

public interface AdressService {

    Optional<Adress> findById (long id);

    List<Adress> findByCity(String city);

    List<Adress> findPostCode(String postCode);

    Adress saveAdress (Adress adress);

    List<Adress> findAll();

    void deleteAdress(long id);
    
}
