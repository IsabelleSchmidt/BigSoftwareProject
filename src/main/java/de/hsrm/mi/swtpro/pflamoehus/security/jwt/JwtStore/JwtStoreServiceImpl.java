package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.JwtStoreServiceException;

/*
 * JwtStoreServiceImpl for implementing the interface 'JwtStoreSerivce'.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class JwtStoreServiceImpl implements JwtStoreService {

    @Autowired
    JwtStoreRepository repo;

    /**
     * Look up if a JwtStore with a given token exists.
     * 
     * @param token wanted token
     * @return boolean
     */
    @Override
    public boolean existsByAccessToken(String token) {
        if (repo.existsByToken(token)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Method for saving a new entry.
     * 
     * @param token to be saved
     * @return saved entry
     */
    @Override
    public JwtStore saveAccessToken(JwtStore token) {
        try {
            token = repo.save(token);

        } catch (OptimisticLockException ole) {

            throw new JwtStoreServiceException();
        }

        return token;

    }

    /**
     * Method for deleting a certain entry.
     * 
     * @param accessToken to be deleted
     */
    @Override
    @Transactional
    public void deleteAccessToken(String accessToken) {
        List<JwtStore> jwt = repo.findByToken(accessToken);

        if (jwt.isEmpty()) {
            throw new JwtStoreServiceException("There is no token with the given access token in the database.");
        }
        
        for(JwtStore token : jwt){
            repo.delete(token);
        }
    }

    /**
     * Find a entry with a certain token.
     * 
     * @param token to be found
     * @return entry
     */
    @Override
    @Transactional
    public JwtStore findByAccessToken(String token) {
        List<JwtStore> jwt = repo.findByToken(token);

        if (jwt.isEmpty()) {
            throw new JwtStoreServiceException("There is no token with the given access token in the database.");
        }

        return jwt.get(0);
    }

    @Override
    public List<JwtStore> findAll() {
        
        return repo.findAll();
    }

}
