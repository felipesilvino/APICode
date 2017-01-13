package code.example.patient.service;

import code.example.patient.EncounterResource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.philips.app.data.DataQuery;
import org.codejargon.fluentjdbc.api.mapper.Mappers;

import javax.ws.rs.PathParam;
import java.util.Collection;
import java.util.Map;

/**
 * Created by aweise on 04/01/17.
 */
@Singleton
@Transactional
public class EncounterService implements EncounterResource {

    @Inject
    private DataQuery query;

    @Override
    public Collection<Map<String, Object>> list(final Integer idPatient) {
        return query.select("select * from encounter where id_patient = :idPatient1")
                    .namedParam("idPatient1", idPatient)
                    .listResult(Mappers.map());
    }

    @Override
    public Map<String, Object> get(final Integer idPatient, final Integer eid) {
        return query.select("select * from encounter where id_patient = :idPatient2 and id_encounter = :eid")
                .namedParam("idPatient2", idPatient)
                .namedParam("eid", eid)
                .firstResult(Mappers.map())
                .orElse(null);
    }

    @Override
    public void release(@PathParam("id") final Integer idPatient, @PathParam("eid") final Integer eid) {
        /// TODO... aws
    }
}
