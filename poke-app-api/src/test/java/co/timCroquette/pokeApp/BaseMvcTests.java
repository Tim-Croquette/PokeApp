package co.timCroquette.pokeApp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import co.timCroquette.pokeApp.configurations.Tokens;
import co.timCroquette.pokeApp.controllers.ControllerMocks;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@Import(ControllerMocks.class)
public class BaseMvcTests {

    protected static final char DELIMITER = '§';

    protected static final int MAX_CHARS_PER_COLUMN = 8192;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Tokens tokens;

    protected final ResultActions perform(String method,
	    String path, String tokenName)
	    throws Exception {
	return perform(method, path, tokenName, null);
    }

    protected final ResultActions perform(String method,
	    String path, String tokenName, String content)
	    throws Exception {
	var builder = requestBuilder(method, path,
		tokenName, content);
	return mvc.perform(builder);
    }

    protected MockHttpServletRequestBuilder requestBuilder(
	    String method, String path, String tokenName,
	    String content) {
	var builder = request(HttpMethod.valueOf(method),
		path);
	if (!"anonymous".equals(tokenName)) {
	    builder.header("Authorization",
		    tokens.get(tokenName));
	}
	if (null != content) {
	    builder.contentType(MediaType.APPLICATION_JSON)
		    .content(content);
	}
	return builder;
    }
}
