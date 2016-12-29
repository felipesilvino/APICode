package code.example;

import com.philips.app.boot.PhilipsBoot;

import java.net.URL;

/**
 * Created by aweise on 28/12/16.
 */
public final class GuiceStart {

    /**
     * Default constructor.
     */
    private GuiceStart() {
        // nothing to do
    }

    public static void main(final String... values) {
        final URL resource = GuiceStart.class.getResource("/configuration.yml");
        final String file = resource.getFile();
        PhilipsBoot.main("-f", file);
    }
}
