package co.timCroquette.pokeApp.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;

import com.auth0.jwt.JWT;

import co.timCroquette.pokeApp.BaseComponentTest;
import co.timCroquette.pokeApp.dtos.Credentials;
import co.timCroquette.pokeApp.dtos.TokenInfo;

public class UserServiceTest extends BaseComponentTest {

    @Autowired
    private UserService service;

    @Value("${pokeApp.tests.accessToken}")
    private String accessToken;
    @Value("${pokeApp.tests.refreshToken}")
    private String refreshToken;

    @Test
    void should_sign_in_when_credentials_are_ok() {
	// Given
	String expectedSubject = "toto@email.com";
	Credentials credentials = new Credentials(
		expectedSubject, "password");
	// When
	TokenInfo actual = service.signIn(credentials);
	String actualSubjectOfAccess = JWT
		.decode(actual.accessToken()).getSubject();
	String actualSubjectOfRefresh = JWT
		.decode(actual.refreshToken()).getSubject();
	// Then
	assertThat(actualSubjectOfAccess)
		.isEqualTo(expectedSubject);
	assertThat(actualSubjectOfRefresh)
		.isEqualTo(expectedSubject);
    }

    @Test
    void should_refresh_token() throws BadRequestException {
	// Given
	Jwt token = toJwt(refreshToken);
	// When
	TokenInfo actual = service.refreshToken(token);
	String actualSubjectOfAccess = JWT
		.decode(actual.accessToken()).getSubject();
	// Then
	assertThat(actual.refreshToken())
		.isEqualTo(token.getTokenValue());
	assertThat(actualSubjectOfAccess)
		.isEqualTo(token.getSubject());
    }

    @Test
    void should_not_refresh_token_when_an_access_token() {
	// Given
	Jwt token = toJwt(accessToken);
	String expected = "You can't refresh access token with an access token";
	// When
	Exception ex = Assertions.assertThrows(
		BadRequestException.class,
		() -> service.refreshToken(token));
	// Then
	assertThat(ex.getMessage()).isEqualTo(expected);
    }
}
