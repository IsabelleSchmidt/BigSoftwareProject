package de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.StatusServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.StatusRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;

/*
 * StatusServiceImpl for implementing the interface 'StatusService'.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepo;

    /**
     * Find a status with a certain code.
     * 
     * @param code to be found
     * @return status
     */
    @Override
    @Transactional
    public Status findStatusWithCode(Statuscode code) {
        Optional<Status> status = statusRepo.findByStatuscode(code);

        if (status.isEmpty()) {
            throw new StatusServiceException("Status was not found!");
        }
        return status.get();
    }

}
