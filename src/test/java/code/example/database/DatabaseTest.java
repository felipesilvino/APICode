package code.example.database;

import code.example.GuiceModuleTest;
import code.example.ServerRequest;
import com.google.inject.Inject;
import com.philips.app.test.junit.ConfigForTesting;
import com.philips.app.test.junit.ModulesForTesting;
import com.philips.app.test.junit.PhilipsJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.*;

/**
 * Created by aweise on 02/01/17.
 */
@RunWith(PhilipsJunit4ClassRunner.class)
@ModulesForTesting(GuiceModuleTest.class)
@ConfigForTesting("/configuration.yml")
public class DatabaseTest {

    @Inject
    private ServerRequest serverRequest;

    @Test
    public void listDatabaseJavaxClient(){
        final Response response = serverRequest.url("databases").request(MediaType.APPLICATION_JSON).get();
        final String expectedValue = "[\"db1\"]";
        final String serverValue = response.readEntity(String.class);
        assert Response.Status.OK.getStatusCode() == response.getStatus();
        assert expectedValue.equals(serverValue);
    }

    @Test
    public void listDatabaseRestAssured(){
        // https://github.com/rest-assured/rest-assured
        serverRequest.when()
            .get("/databases")
            .then()
            .statusCode(200)
            .body(containsString("db1"));
    }
}
