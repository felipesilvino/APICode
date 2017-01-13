package code.example.patient;

import code.example.constant.AuthorizationFlag;
import com.philips.security.commons.responses.ExceptionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
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
@Api(value = "encounters", tags = {"encounters"}, protocols = "https")
public interface EncounterResource {

    @GET
    @ApiOperation(code = HttpURLConnection.HTTP_OK,
            value = "Retrieves a list containing the given patient's encounters.",
            responseContainer = "List")
    @RequiresPermissions(AuthorizationFlag.Permission.ENCOUNTER_RETRIEVE)
    Collection<Map<String, Object>> list(@PathParam("id") Integer idPatient);

    @GET
    @Path("{eid}")
    @RequiresPermissions(AuthorizationFlag.Permission.ENCOUNTER_RETRIEVE)
    Map<String, Object> get(@PathParam("id") Integer idPatient, @PathParam("eid") Integer eid);

    @PUT
    @Path("{eid}/release")
    @RequiresPermissions(AuthorizationFlag.Permission.ENCOUNTER_RELEASE)
    void release(@PathParam("id") Integer idPatient, @PathParam("eid") Integer eid);

}
