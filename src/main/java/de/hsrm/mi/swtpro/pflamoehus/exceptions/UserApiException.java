package de.hsrm.mi.swtpro.pflamoehus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserApiException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserApiException(String msg){
        super(msg);
    }
    
}
