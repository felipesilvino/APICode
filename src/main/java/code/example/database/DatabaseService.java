package code.example.database;

import code.example.database.listener.DatabaseScript;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.philips.app.data.DataQuery;
import com.philips.app.data.ds.DataSourceProvider;
import com.philips.app.data.sql.SQLResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aweise on 03/01/17.
 */
@Singleton
@Transactional
public class DatabaseService implements DatabaseResources {

    @Inject
    private DataSourceProvider dataSource;

    @Inject
    private DataQuery query;

    @Override
    public List<String> list(final String name) throws Exception {
        List<String> sources = dataSource.getAvailableDataSources();
        if (name != null && !name.isEmpty()) {
            sources = sources
                    .stream()
                    .filter(s -> s.equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }
        return sources;
    }

    @Override
    public List<String> scripts() throws Exception {
        final List<String> lsCommands = new ArrayList<>();
        final InputStream resource = DatabaseScript.class.getResourceAsStream("/script.sql");
        SQLResource.read(resource, cs -> {
            final String value = cs.trim().replaceAll(";", "");
            if (!value.isEmpty()) {
                lsCommands.add(value);
            }
        });
        lsCommands.forEach(fe -> query.update(fe).run());
        return lsCommands;
    }
}
