package code.example;

import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.philips.app.boot.PhilipsInjector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aweise on 30/12/16.
 */
public class GuiceModuleTest extends AbstractModule {

    @Override
    protected void configure() {
        install(loadBootConfig(0).withModules());
    }

    static PhilipsInjector loadBootConfig(final int port){
        try(InputStream is = GuiceStart.class.getResourceAsStream("/configuration.yml")){
            return PhilipsInjector.fromStream(is).configBoot(cs -> cs.withPort(port));
        } catch (IOException e) {
            throw new ProvisionException("not found configuration", e);
        }
    }
}
