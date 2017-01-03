package code.example;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.philips.app.boot.server.ServerPort;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static io.restassured.RestAssured.given;

/**
 * Created by aweise on 03/01/17.
 */
@Singleton
public class ServerRequest {

    private static final String URL = "http://localhost:%s/resources/%s";

    @Inject
    private ServerPort port;

    public RequestSpecification when(){
        return given()
                .proxy("localhost", port.getPort())
                .basePath("/resources")
                .contentType(ContentType.JSON)
                .when();
    }

    public WebTarget url(String service) {
        int portNumber = port.getPort();
        return ClientBuilder.newClient().target(String.format(URL, portNumber, service));
    }

}
