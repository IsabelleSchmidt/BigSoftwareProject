package de.hsrm.mi.swtpro.pflamoehus.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Overrides the method commence(), which will be triggered anytime
 * unauthenticated User request a secured HTTP
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    
    /** 
     * 
     * This method will be triggerd anytime unauthenticated 
     * User requests a secured HTTP resource and an AuthenticationException is thrown.
     * 
     * @param request that is incoming
     * @param response that has to be sent
     * @param authException authenticationExcpetion
     * @throws IOException general class exception
     * @throws ServletException servlet exception
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error:  " + authException.getMessage());
		response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Error: Unauthorized"); //406 Status code

    }
    
}
