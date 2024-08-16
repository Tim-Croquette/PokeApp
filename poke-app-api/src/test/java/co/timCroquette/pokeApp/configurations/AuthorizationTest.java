package co.timCroquette.pokeApp.configurations;

import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import co.timCroquette.pokeApp.BaseMvcTests;

public class AuthorizationTest extends BaseMvcTests {

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @DisplayName(value = "Authorized token should pass")
    @CsvFileSource(resources = "/csv/authorized.csv", delimiter = DELIMITER)
    void shouldAuthorizedTokenPass(String method,
	    String path, String tokenName)
	    throws Exception {
	mvc.perform(requestBuilder(method, path, tokenName,
		null)).andExpectAll(status().is(not(401)),
			status().is(not(403)));
    }

    @ParameterizedTest
    @DisplayName(value = "Denied token should not pass")
    @CsvFileSource(resources = "/csv/denied.csv", delimiter = DELIMITER)
    void shouldDeniedTokenNotPass(String method,
	    String path, String tokenName,
	    int expectedStatus) throws Exception {
	mvc.perform(requestBuilder(method, path, tokenName,
		null))
		.andExpectAll(status().is(expectedStatus));
    }

}
