package co.timCroquette.pokeApp.controllers;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ControllerMocks {

    @Bean
    UserController userController() {
	return Mockito.mock(UserController.class);
    }

}
