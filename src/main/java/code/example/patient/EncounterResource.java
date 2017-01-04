package code.example.patient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;

/**
 * Created by aweise on 04/01/17.
 * Se usar o @Path apenas dentro do {@link PatientResource} precisa registrar no Guice manualmente essa
 * classe, como foi feito no {@link code.example.database.DatabaseResources}
 */
@Path("{id}/encounters")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EncounterResource {

    @GET
    Collection<Map<String, Object>> list(@PathParam("id") Integer idPatient);

    @GET
    @Path("{eid}")
    Map<String, Object> get(@PathParam("id") Integer idPatient, @PathParam("eid") Integer eid);

}
