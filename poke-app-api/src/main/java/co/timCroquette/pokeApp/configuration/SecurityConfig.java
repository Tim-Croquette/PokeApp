package co.timCroquette.pokeApp.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Value("${pokeApp.auth.issuer}")
    private String issuer;
    @Value("${pokeApp.auth.secret}")
    private String secret;

    @Bean
    public AuthHelper authHelper() {
	Algorithm algorithm = Algorithm.HMAC256(secret);

	return new AuthHelper(issuer, algorithm);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
	    throws Exception {
	http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((authz) -> authz
			.requestMatchers("/sign-in",
				"/sign-up")
			.permitAll().anyRequest()
			.authenticated())
		.oauth2ResourceServer((
			oauth2ResourceServer) -> oauth2ResourceServer
				.jwt(Customizer
					.withDefaults()));
	return http.build();
    }

    @Bean
    JwtAuthenticationConverter authentificationConverter() {
	return new JwtAuthenticationConverter();
    }

    @Bean
    JwtDecoder jwtdecoder() throws Exception {
	SecretKey secretKey = new SecretKeySpec(
		secret.getBytes(), "Hmacsha256");
	return NimbusJwtDecoder.withSecretKey(secretKey)
		.build();
    }

}
