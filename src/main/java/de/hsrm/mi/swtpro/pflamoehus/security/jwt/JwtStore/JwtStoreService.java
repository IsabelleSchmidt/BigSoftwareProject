package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;


public interface JwtStoreService {


    boolean existsByAccessToken(String token);

    JwtStore saveAccessToken(JwtStore token);

    void deleteAccessToken(String accessToken);

    JwtStore findByAccessToken(String token);
    
}
