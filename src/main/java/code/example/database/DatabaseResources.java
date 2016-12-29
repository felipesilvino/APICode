package code.example.database;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.philips.app.data.ds.DataSourceProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aweise on 28/12/16.
 */
@Transactional
@Path("databases")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResources {

    @Inject
    private DataSourceProvider dataSource;

    @GET
    public List<String> list(@QueryParam("name") final String name) throws Exception {
        List<String> sources = dataSource.getAvailableDataSources();
        if (name != null && !name.isEmpty()) {
            sources = sources
                    .stream()
                    .filter(s -> s.equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }
        return sources;
    }

}
