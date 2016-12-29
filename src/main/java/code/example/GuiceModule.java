package code.example;

import code.example.database.guice.DatabaseModule;
import com.google.inject.servlet.ServletModule;
import com.philips.app.commons.scanner.ReflectionsFactory;
import com.philips.app.commons.scanner.ScannerFactory;
import com.philips.app.jaxrs.guice.ResteasyModule;

/**
 * Created by aweise on 28/12/16.
 */
public class GuiceModule extends ServletModule {

    private static final ScannerFactory RF = new ReflectionsFactory("code.example", "com.philips");

    @Override
    protected void configureServlets() {
        install(new DatabaseModule(RF));
        install(new ResteasyModule(RF));
    }

}
