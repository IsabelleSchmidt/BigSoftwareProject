package de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice;

import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;

public interface StatusService {

    public Status findStatusWithCode(String code);
    
}
