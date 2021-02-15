package de.hsrm.mi.swtpro.pflamoehus.user.roles.rolesservice;

import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;

/*
 * RoleSerivce for different operations to apply on the roles.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface RoleService {

    /**
     * Find a role by its name.
     * 
     * @param name to be found
     * @return role
     */
    Roles findByName(ERoles name);

    /**
     * Save a new role.
     * 
     * @param role new role
     * @return role
     */
    Roles saveRole(Roles role);
}
