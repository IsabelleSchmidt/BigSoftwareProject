package de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequest;

public interface PasswordRequestService {
    
    public PasswordRequest searchRequestWithEmail(String email);

    public List<PasswordRequest> findAll();

    public String getRandomString();

}
