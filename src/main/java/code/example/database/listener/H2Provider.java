package code.example.database.listener;

import com.google.inject.ProvisionException;
import com.google.inject.Singleton;
import com.netflix.governator.spi.LifecycleListener;
import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by aweise on 28/12/16.
 */
@Singleton
public class H2Provider implements LifecycleListener {

    private Server server;

    @Override
    public void onStarted() {
        try {
            server = Server.createTcpServer();
            server.start();
        } catch (SQLException e) {
            throw new ProvisionException("start h2 error", e);
        }
    }

    @Override
    public void onStopped(final Throwable error) {
        if (server != null && server.isRunning(false)) {
            server.stop();
        }
    }
}
