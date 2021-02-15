package de.hsrm.mi.swtpro.pflamoehus.user.roles.rolesservice;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.RoleServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;

/*
 * RoleServiceImpl for implementing the interface 'RoleService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RolesRepository roleRepo;

    /**
     * Find a role by its name.
     * 
     * @param name to be found
     * @return role
     */
    @Override
    public Roles findByName(ERoles name) {
        Optional<Roles> role = roleRepo.findByName(name);
        if (!role.isPresent()) {
            throw new RoleServiceException();
        }

        return role.get();

    }

    /**
     * Save a new role.
     * 
     * @param role new role
     * @return role
     */
    @Override
    public Roles saveRole(Roles role) {
        return roleRepo.save(role);
    }

}
