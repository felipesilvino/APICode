package code.example.database;

import code.example.GuiceModuleTest;
import com.google.inject.Inject;
import com.netflix.governator.guice.test.ModulesForTesting;
import com.netflix.governator.guice.test.junit4.GovernatorJunit4ClassRunner;
import com.philips.app.boot.server.ServerPort;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static  junit.framework.Assert.*;

/**
 * Created by aweise on 02/01/17.
 */
@RunWith(GovernatorJunit4ClassRunner.class)
@ModulesForTesting(GuiceModuleTest.class)
public class TableResourcesTest {

    private static final String URL = "http://localhost:%s/resources/%s";

    @Inject
    private ServerPort port;

    @Test
    public void listDatabase(){
        final Response response = url("databases").request(MediaType.APPLICATION_JSON).get();
        final String expectedValue = "[\"db1\"]";
        final String serverValue = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedValue, serverValue);
    }

    private WebTarget url(String service) {
        int portNumber = port.getPort();
        return ClientBuilder.newClient().target(String.format(URL, portNumber, service));
    }

}
