package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * JwtStore-Repository for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface JwtStoreRepository extends JpaRepository<JwtStore, Long>{

    /**
     * Look up if a JwtStore with a given token exists.
     * 
     * @param token wanted token
     * @return boolean
     */
    boolean existsByToken (String token);

    /**
     * Find all token.
     * 
     * @return all JwtStores
     */
    List<JwtStore> findAll();

    /**
     * Find a entry with a certain id.
     * 
     * @param id to be found
     * @return entry
     */
    Optional<JwtStore> findJwtStoreById(Long id);

    /**
     * Find a entry with a certain token.
     * 
     * @param token to be found
     * @return entry
     */
    List<JwtStore> findByToken(String token);
    
}
