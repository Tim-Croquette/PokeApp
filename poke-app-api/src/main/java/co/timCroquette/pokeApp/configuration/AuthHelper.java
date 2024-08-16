package co.timCroquette.pokeApp.configuration;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class AuthHelper {

    private final String issuer;
    private final Algorithm algorithm;

    @Value("${pokeApp.auth.expiration}")
    private long expiration;
    @Value("${pokeApp.auth.refreshExpiration}")
    private long refreshExpiration;

    public AuthHelper(String issuer, Algorithm algorithm) {
	this.issuer = issuer;
	this.algorithm = algorithm;
    }

    private String generateJWT(String subject,
	    long expiration) {
	Instant now = Instant.now();
	Instant expirationTime = now
		.plusSeconds(expiration);
	return JWT.create().withIssuer(issuer)
		.withSubject(subject).withIssuedAt(now)
		.withExpiresAt(expirationTime)
		.sign(algorithm);
    }

    public String createJWT(String subject) {
	return generateJWT(subject, this.expiration);
    }

    public String createRefreshJWT(String subject) {
	return generateJWT(subject, refreshExpiration);
    }

    public Boolean isAnAccessToken(Jwt token) {
	long issueAt = token.getIssuedAt().getEpochSecond();
	long expiredAt = token.getExpiresAt()
		.getEpochSecond();
	return ((expiredAt - issueAt) == expiration);
    }
}
