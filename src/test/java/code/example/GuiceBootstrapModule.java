package code.example;

import com.netflix.governator.guice.BootstrapBinder;
import com.netflix.governator.guice.BootstrapModule;

/**
 * Created by aweise on 30/12/16.
 */
public class GuiceBootstrapModule implements BootstrapModule {

    @Override
    public void configure(BootstrapBinder binder) {
        binder.install(GuiceModuleTest.loadBootConfig(0).withModules());
        binder.bind(ServerRequest.class);
    }

}
