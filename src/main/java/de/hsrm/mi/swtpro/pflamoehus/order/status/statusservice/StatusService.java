package de.hsrm.mi.swtpro.pflamoehus.order.status.statusservice;

import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;

/*
 * StatusService for different operations to apply on status.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public interface StatusService {

    /**
     * Find a status with a certain code.
     * 
     * @param code to be found
     * @return status
     */
    public Status findStatusWithCode(Statuscode code);

}
