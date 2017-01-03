package code.example.database.listener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.netflix.governator.spi.LifecycleListener;
import com.philips.app.boot.server.ServerPort;

import javax.ws.rs.client.ClientBuilder;

/**
 * Created by aweise on 29/12/16.
 */
@Singleton
public class DatabaseScript implements LifecycleListener {

    private static final String URL = "http://localhost:%s/resources/databases/scripts";

    @Inject
    private Provider<ServerPort> portProvider;

    @Override
    public void onStarted() {
        // workaround, dataQuery needs a request scope.. :(
        final ServerPort serverPort = portProvider.get();
        final int portNumber = serverPort.getPort();
        ClientBuilder.newClient().target(String.format(URL, portNumber)).request().get();
    }

    @Override
    public void onStopped(final Throwable error) {
        // nothing to do
    }
}
