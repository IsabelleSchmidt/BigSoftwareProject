package de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.OrderServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.StatusRepository;


@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepo;

    @Override
    public Status findStatusWithCode(String code) {
        Optional<Status> status = statusRepo.findByStatuscode(code);
        
		if(status.isEmpty()){
            throw new OrderServiceException("Status was not found!");
        } return status.get();
	}
    
}
