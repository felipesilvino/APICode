package code.example;

import com.google.inject.Module;
import com.google.inject.ProvisionException;
import com.google.inject.util.Modules;
import com.netflix.governator.ShutdownHookModule;
import com.netflix.governator.guice.BootstrapBinder;
import com.netflix.governator.guice.BootstrapModule;
import com.netflix.governator.guice.jetty.JettyModule;
import com.philips.app.boot.PhilipsModule;
import com.philips.app.boot.model.BootConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by aweise on 30/12/16.
 */
public class GuiceBootstrapModule implements BootstrapModule {

    @Override
    public void configure(BootstrapBinder binder) {
        final BootConfig bc = loadBootConfig();
        bc.setPort(0);
        final Module modules = Modules
                                .override(new ShutdownHookModule(), new JettyModule(), new GuiceModule())
                                .with(new PhilipsModule(bc));
        binder.install(modules);
    }

    private static BootConfig loadBootConfig(){
        final URL resource = GuiceStart.class.getResource("/configuration.yml");
        final String file = resource.getFile();
        try (InputStream is = new FileInputStream(file)) {
            return new Yaml().loadAs(is, BootConfig.class);
        }
        catch (IOException e) {
            throw new ProvisionException("Error reading the config file", e);
        }
    }
}
