package co.timCroquette.pokeApp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Tokens {

    @Value("${pokeApp.tests.validToken}")
    private String validToken;

    @Value("${pokeApp.tests.unvalidToken}")
    private String unvalidToken;

    @Value("${pokeApp.tests.expiredToken}")
    private String expiredToken;

    @Value("${pokeApp.tests.noToken}")
    private String noToken;

    public String get(final String name) {
	switch (name) {
	case "validToken":
	    return validToken;
	case "unvalidToken":
	    return unvalidToken;
	case "expiredToken":
	    return expiredToken;
	case "noToken":
	    return noToken;
	default:
	    throw new IllegalArgumentException(
		    "Unexpected value: " + name);
	}
    }
}
