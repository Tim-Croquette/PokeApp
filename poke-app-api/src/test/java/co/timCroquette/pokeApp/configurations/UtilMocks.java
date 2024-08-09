package co.timCroquette.pokeApp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.auth0.jwt.algorithms.Algorithm;

import co.timCroquette.pokeApp.configuration.AuthHelper;

@TestConfiguration
public class UtilMocks {

    @Value("${pokeApp.auth.issuer}")
    private String issuer;
    @Value("${pokeApp.auth.secret}")
    private String secret;

    @Bean
    AuthHelper authHelper() {
	Algorithm algorithm = Algorithm.HMAC256(secret);

	return new AuthHelper(issuer, algorithm);
    }

}