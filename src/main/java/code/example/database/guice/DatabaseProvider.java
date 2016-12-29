package code.example.database.guice;

import com.google.inject.Inject;
import com.philips.app.data.ds.DataSourceProvider;
import org.codejargon.fluentjdbc.api.integration.ConnectionProvider;
import org.codejargon.fluentjdbc.api.integration.QueryConnectionReceiver;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by aweise on 28/12/16.
 */
public class DatabaseProvider implements ConnectionProvider {

    @Inject
    private DataSourceProvider dataSource;

    private Optional<String> databaseName = Optional.empty();

    @Override
    public void provide(final QueryConnectionReceiver query) throws SQLException {
        try {
            final String name = getFirstDatabase();
            final DataSource ds = dataSource.getDataSoure(name);
            query.receive(ds.getConnection());
        } catch (NamingException e) {
            throw new SQLException(e);
        }
    }

    private String getFirstDatabase() throws NamingException {
        if (!databaseName.isPresent()) {
            databaseName = dataSource.getAvailableDataSources().stream().findFirst();
        }
        return databaseName.orElse(null);
    }
}
