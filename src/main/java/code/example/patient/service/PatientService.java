package code.example.patient.service;

import code.example.patient.EncounterResource;
import code.example.patient.PatientResource;
import code.example.patient.model.PatientEntity;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.philips.app.data.DataQuery;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by aweise on 03/01/17.
 */
@Singleton
@Transactional
public class PatientService implements PatientResource {

    public static final String ID_PATIENT_SQL = "ID_PATIENT";
    public static final String DS_NAME_SQL = "DS_NAME";

    @Inject
    private DataQuery query;

    @Inject
    private EncounterResource encounterInjected;


    @Override
    public Collection<PatientEntity> list() {
        return query.select("select ID_PATIENT, DS_NAME from patient").listResult(lr -> {
            final PatientEntity pe = new PatientEntity();
            pe.setId(lr.getInt(ID_PATIENT_SQL));
            pe.setName(lr.getString(DS_NAME_SQL));
            return pe;
        });
    }

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
                    pe.setId(fr.getInt(ID_PATIENT_SQL));
                    pe.setName(fr.getString(DS_NAME_SQL));
                    return pe;
                }).orElse(null);

        if (entity != null) {
            entity.setEncounters(Link.fromPath("resources/patients/{code}/encounters")
                                        .type(MediaType.APPLICATION_JSON)
                                        .build(code));
        }

        return entity;
    }

    @Override
    public Integer create(final PatientEntity entity) {
        Preconditions.checkNotNull(entity);

        final String sql = "insert into patient(ds_name) values (:dsName)";
        final UpdateResultGenKeys<Integer> dsName = query.update(sql)
                .namedParam("dsName", entity.getName())
                .runFetchGenKeys(Mappers.singleInteger());

        final Optional<Integer> oKey = dsName.firstKey();

        return oKey.orElse(0);
    }

    @Override
    public boolean update(final Integer id, final Map<String, Object> name) {
        final String sql = "update patient set ds_name = :dsName2 where id_patient = :idPatient2";
        final UpdateResult updateResult = query.update(sql).namedParam("dsName2", name.get("ds_name"))
                .namedParam("idPatient2", id).run();
        return updateResult.affectedRows() > 0;
    }

    @Override
    public void delete(final Integer id) {
        final String sql = "delete from patient where id_patient = :idPatient3";
        query.update(sql).namedParam("idPatient3", id).run();
    }

    @Override
    public EncounterResource encounterResource() {
        return encounterInjected;
    }
}
