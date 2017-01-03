package code.example.patient;

import code.example.patient.model.PatientEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by aweise on 03/01/17.
 */
@Path("patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PatientResource {

    @GET
    @Path("{id}")
    Response get(@PathParam("id") Integer code);

    @GET
    @Path("/entity/{id}")
    PatientEntity entity(@PathParam("id") Integer code);

}
