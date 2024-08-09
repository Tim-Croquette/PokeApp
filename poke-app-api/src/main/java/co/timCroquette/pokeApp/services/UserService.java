package co.timCroquette.pokeApp.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import co.timCroquette.pokeApp.configuration.AuthHelper;
import co.timCroquette.pokeApp.dtos.Credentials;
import co.timCroquette.pokeApp.dtos.TokenInfo;

@Service
public class UserService {

    @Autowired
    private AuthHelper authHelper;

    public TokenInfo signIn(Credentials credentials) {
	String email = credentials.email();
	return new TokenInfo(authHelper.createJWT(email),
		authHelper.createRefreshJWT(email));
    }

    public TokenInfo refreshToken(Jwt token)
	    throws BadRequestException {
	if (authHelper.isAnAccessToken(token)) {
	    throw new BadRequestException(
		    "You can't refresh access token with an access token");
	}
	String subject = token.getSubject();
	String newToken = authHelper.createJWT(subject);
	String oldToken = token.getTokenValue();
	return new TokenInfo(newToken, oldToken);
    }

}
