package de.hsrm.mi.swtpro.pflamoehus.exceptions.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserApiExecption for errors in the RestController.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public UserApiException(String msg) {
        super(msg);
    }

}
