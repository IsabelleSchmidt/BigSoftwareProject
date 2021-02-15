package de.hsrm.mi.swtpro.pflamoehus.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailServiceImpl;
/**
 * Executes once per request.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;
    /** 
     * What gets done in this method:
     * 1. Gets jwt from authorization header (by removing Bearer prefix)
     * 2. If the request has jwt, validate ist, parse email from it
     * 3. from email, get userdetails to create an authentication object
     * 4. set the current userdetails in securitycontext using setAuthentication(authentication) method
     * 
     * 
     * @param request request that has been sent
     * @param response response that has to be sent
     * @param filterChain giving view into the invocation chain of a filtered request for a resource
     * @throws ServletException general exception a servlet can throw
     * @throws IOException general class exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{

            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                String email = jwtUtils.getUserNameFromJwtToken(jwt);   //Parsing email from jwt
                UserDetails ud = userDetailsService.loadUserByUsername(email);  //from email we get the UserDetails to create an Authentication object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  //set the UserDetails in SecurityContext

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{
                
            }
        }catch(Exception e){
            logger.error("Cannot set user authentication " + e);

        }

        filterChain.doFilter(request, response);

    }

    
    /** 
     * Removes the Bearer prefix from the header.
     * 
     * @param request request with header, that has been sent
     * @return String
     */
    //Removing Bearer prefix to get JWT from the Authorization header
    public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
    
}
