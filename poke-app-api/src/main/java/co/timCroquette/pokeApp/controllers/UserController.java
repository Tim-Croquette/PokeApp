package co.timCroquette.pokeApp.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.timCroquette.pokeApp.dtos.Credentials;
import co.timCroquette.pokeApp.dtos.TokenInfo;
import co.timCroquette.pokeApp.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public TokenInfo signIn(
	    @RequestBody Credentials inputs) {
	return userService.signIn(inputs);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void signUp() {

    }

    @PostMapping("/refresh-token")
    public TokenInfo getResource(
	    JwtAuthenticationToken principal)
	    throws BadRequestException {
	Jwt token = principal.getToken();
	return userService.refreshToken(token);
    }

}
