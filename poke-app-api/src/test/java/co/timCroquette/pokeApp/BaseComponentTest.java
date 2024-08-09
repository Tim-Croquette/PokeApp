package co.timCroquette.pokeApp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootTest
@ActiveProfiles(value = "test")
public class BaseComponentTest {

    protected static final char DELIMITER = '§';

    protected static final int MAX_CHARS_PER_COLUMN = 8192;

    protected Jwt toJwt(String token) {
	DecodedJWT decodedToken = JWT.decode(token);
	return Jwt.withTokenValue(token)
		.claim("claims", decodedToken.getClaims())
		.header("header", decodedToken.getHeader())
		.issuedAt(
			decodedToken.getIssuedAtAsInstant())
		.expiresAt(decodedToken
			.getExpiresAtAsInstant())
		.build();
    }
}
