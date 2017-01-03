package code.example.database;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by aweise on 28/12/16.
 */
@Path("databases")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface DatabaseResources {

    @GET
    List<String> list(@QueryParam("name") final String name) throws Exception;

    @GET
    @Path("scripts")
    List<String> scripts() throws Exception;

}
