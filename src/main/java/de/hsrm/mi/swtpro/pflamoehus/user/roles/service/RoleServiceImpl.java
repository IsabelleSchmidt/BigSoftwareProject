package de.hsrm.mi.swtpro.pflamoehus.user.roles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.RoleServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired 
    RolesRepository roleRepo;


    @Override
    public Roles findByName(ERoles name) {
       Optional<Roles> role = roleRepo.findByName(name);
       if(!role.isPresent()){
            throw new RoleServiceException();
       }

       return role.get();
           
       }
    

    @Override
    public Roles saveRole(Roles role) {
        return null;
    }
    
}
