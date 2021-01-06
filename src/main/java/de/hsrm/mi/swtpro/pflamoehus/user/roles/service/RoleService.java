package de.hsrm.mi.swtpro.pflamoehus.user.roles.service;

import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;

public interface RoleService {

    Roles findByName(ERoles name);
    
    Roles saveRole(Roles role);
}
