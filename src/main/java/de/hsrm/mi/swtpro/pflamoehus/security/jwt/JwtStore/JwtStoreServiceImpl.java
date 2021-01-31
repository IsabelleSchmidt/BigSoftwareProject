package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.JwtStoreServiceException;



@Service
public class JwtStoreServiceImpl implements JwtStoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtStoreServiceImpl.class);

    @Autowired
    JwtStoreRepository repo;

    @Override
    public boolean existsByAccessToken(String token) {
        if (repo.existsByToken(token)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public JwtStore saveAccessToken(JwtStore token) {
        try {
            token = repo.save(token);

        } catch (OptimisticLockException ole) {

            throw new JwtStoreServiceException();
        }

        return token;

    }

    @Override
    @Transactional
    public void deleteAccessToken(String accessToken) {
        JwtStore jwt = findByAccessToken(accessToken);
        repo.delete(jwt);
    }

    @Override
    @Transactional
    public JwtStore findByAccessToken(String token) {
        Optional<JwtStore> jwt = repo.findByToken(token);

        if (jwt.isEmpty()) {
            throw new JwtStoreServiceException("There is no token with the given access token in the database.");
        }

        return jwt.get();
    }

}
