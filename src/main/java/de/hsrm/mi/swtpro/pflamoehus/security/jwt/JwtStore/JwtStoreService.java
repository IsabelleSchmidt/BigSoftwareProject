package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import java.util.List;

/*
 * JwtStoreService for different operations to apply on the jwt store.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface JwtStoreService {

    /** 
     * Look up if a JwtStore with a given token exists.
     * 
     * @param token wanted token
     * @return boolean
     */
    boolean existsByAccessToken(String token);

    /** 
     * Method for saving a new entry.
     * 
     * @param token to be saved
     * @return saved entry
     */
    JwtStore saveAccessToken(JwtStore token);

    /** 
     * Method for deleting a certain entry.
     * 
     * @param accessToken to be deleted
     */
    void deleteAccessToken(String accessToken);

    /** 
     * Find a entry with a certain token.
     * 
     * @param token to be found
     * @return entry
     */
    JwtStore findByAccessToken(String token);

    List<JwtStore> findAll();
    
}
