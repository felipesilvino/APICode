package code.example.database.guice;

import code.example.database.DatabaseResources;
import code.example.database.listener.DatabaseScript;
import code.example.database.listener.H2Provider;
import code.example.database.DatabaseService;
import com.google.inject.AbstractModule;
import com.philips.app.commons.scanner.ScannerFactory;
import com.philips.app.data.DataModule;

/**
 * Created by aweise on 28/12/16.
 */
public class DatabaseModule extends AbstractModule {

    private final ScannerFactory scannerFactory;

    public DatabaseModule(final ScannerFactory scannerFactory) {
        this.scannerFactory = scannerFactory;
    }

    @Override
    protected void configure() {
        bind(H2Provider.class).asEagerSingleton();
        install(new DataModule(scannerFactory, new DatabaseProvider()));
        bind(DatabaseScript.class).asEagerSingleton();
        bind(DatabaseResources.class).to(DatabaseService.class);
    }
}
