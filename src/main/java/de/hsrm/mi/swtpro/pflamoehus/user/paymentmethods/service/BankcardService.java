package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service;

import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;

public interface BankcardService {

    Optional<Bankcard> findById(long id);

    Bankcard saveBankcard(Bankcard card);

    void deleteBankcard(long id);
    
}
