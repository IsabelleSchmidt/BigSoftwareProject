package de.hsrm.mi.swtpro.pflamoehus.exceptions.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ProductApiExecption for errors in the RestController.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg the message that has to be printed
     */
    public ProductApiException(String msg) {
        super(msg);
    }
}