package co.timCroquette.pokeApp.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import co.timCroquette.pokeApp.BaseComponentTest;
import co.timCroquette.pokeApp.configuration.AuthHelper;

class AuthHelperTest extends BaseComponentTest {

    @Autowired
    private AuthHelper authHelper;

    @Test
    void shouldDetectAnAccessToken() {
	// Given
	String subject = "toto";
	// When
	String accessToken = authHelper.createJWT(subject);
	String refreshToken = authHelper
		.createRefreshJWT(subject);
	// Then
	assertThat(authHelper
		.isAnAccessToken(toJwt(accessToken)))
		.isTrue();
	assertThat(authHelper
		.isAnAccessToken(toJwt(refreshToken)))
		.isFalse();
    }

}
