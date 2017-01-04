package code.example.patient;

import code.example.patient.model.PatientEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;

/**
 * Created by aweise on 03/01/17.
 */
@Path("patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PatientResource {


    @GET
    Collection<PatientEntity> list();

    @GET
    @Path("{id}")
    Response get(@PathParam("id") Integer code);

    @GET
    @Path("entity/{id}")
    PatientEntity entity(@PathParam("id") Integer code);

    @POST
    Integer create(PatientEntity entity);

    @PUT
    @Path("{id}")
    boolean update(@PathParam("id") Integer id, Map<String, Object> values);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Integer id);

    @Path("{id}/encounters")
    EncounterResource encounterResource();

}
