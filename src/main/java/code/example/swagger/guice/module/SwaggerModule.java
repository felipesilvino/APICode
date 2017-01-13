package code.example.swagger.guice.module;

import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * @author Felipe Silvino Pereira
 * @version 1.0
 * @linktourl https://github.com/felipesilvino
 */
public class SwaggerModule extends ServletModule {

    /* (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configureServlets() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setDescription("APICode - Documentation");
        beanConfig.setTitle("APICode");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("http://localhost:8084");
        beanConfig.setBasePath(getServletContext().getContextPath());
        beanConfig.setResourcePackage("code.example.patient, code.example.swagger");
        beanConfig.setScan(true);

        bind(BeanConfig.class).toInstance(beanConfig);
    }
}
