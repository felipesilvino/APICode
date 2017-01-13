package code.example;

import code.example.database.guice.DatabaseModule;
import com.google.inject.servlet.ServletModule;
import com.philips.app.commons.scanner.ReflectionsFactory;
import com.philips.app.commons.scanner.ScannerFactory;
import com.philips.app.jaxrs.guice.ResteasyModule;
import com.philips.security.client.guice.modules.SecurityModule;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import code.example.swagger.guice.module.SwaggerModule;

/**
 * Created by aweise on 28/12/16.
 */
public class GuiceModule extends ServletModule {

    private static final ScannerFactory RF = new ReflectionsFactory("code.example", "com.philips", "io.swagger.jaxrs.listing");

    @Override
    protected void configureServlets() {
        super.configureServlets();

        /**
         * Security module binds a filter that should be the first filter in the servlet's filter chain,
         * right after the Guice's filter.
         *
         * Therefore, tit must be kept as the first installed module.
         */
        install(new SecurityModule(Path.class));

        /**
         * Other modules
         */
        install(new SwaggerModule());
        install(new DatabaseModule(RF));

        /**
         * Reastesy does not chain down the received requests, due to this behaviour, it must be kept as the
         * last installed module.
         */
        ResteasyModule resteasyModule = new ResteasyModule(RF);
        resteasyModule
                .withCors()
                .allowMethod(HttpMethod.GET)
                .allowOrigin("*");

        install(resteasyModule);
    }

}
