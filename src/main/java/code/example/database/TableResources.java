package code.example.database;

import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.philips.app.data.DataQuery;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.h2.tools.Csv;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by aweise on 29/12/16.
 */
@Transactional
@Path("tables")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TableResources {

    @Inject
    private DataQuery query;

    @GET
    public List<Map<String, Object>> values() {
        return query
                .select("select id, when, desc, tag from test")
                .listResult(Mappers.map());
    }

    @PUT
    @Path("{code}")
    public void update(final @PathParam("code") Integer id, final List<Object> values) {
        //query.update("update test ")
    }

    @GET
    @Path("createTable")
    public Response createTable() {
        Response.ResponseBuilder response = Response.ok();
        try {

            query
                .update("CREATE TABLE TEST(ID INT PRIMARY KEY, WHEN TIMESTAMP, DESC VARCHAR(255), TAG VARCHAR(255))")
                .run();

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            final String file = DatabaseResources.class.getResource("/desc-values.csv").getFile();
            try (final ResultSet read = new Csv().read(file, null, null)) {
                while (read.next()) {
                    query.update("insert into TEST values (?, ?, ?, ?)")
                            .params(read.getInt(1), sdf.parse(read.getString(2)), read.getString(3), read.getString(4))
                            .run();
                }
            }

        } catch (Exception e) {
            response = Response.serverError().entity(Throwables.getStackTraceAsString(e));
        }

        return response.build();
    }
}
