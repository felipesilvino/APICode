package code.example.patient.service;

import code.example.patient.PatientResource;
import code.example.patient.model.PatientEntity;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.philips.app.data.DataQuery;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

/**
 * Created by aweise on 03/01/17.
 */
@Singleton
public class PatientService implements PatientResource {

    @Inject
    private DataQuery query;

    @Override
    public Response get(final Integer code) {
        final PatientEntity entity = entity(code);
        final Response response;
        if (entity != null) {
            response = Response.ok(entity).build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }

    @Override
    public PatientEntity entity(final Integer code) {
        final String sqlPatient = "select ID_PATIENT, DS_NAME from patient where id_patient = :code";
        final PatientEntity entity = query
                .select(sqlPatient)
                .namedParam("code", code)
                .firstResult(fr -> {
                    final PatientEntity pe = new PatientEntity();
                    pe.setId(fr.getInt("ID_PATIENT"));
                    pe.setName(fr.getString("DS_NAME"));
                    return pe;
                }).orElse(null);

        if (entity != null) {
            entity.setMedicines(Link.fromPath("/patient/{code}/medicines").build(code));
        }

        return entity;
    }

}
