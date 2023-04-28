package org.univaq.swa.examples.base;

import org.univaq.swa.examples.security.CORSFilter;
import org.univaq.swa.examples.security.AppExceptionMapper;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.univaq.swa.examples.jackson.ObjectMapperContextResolver;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.univaq.swa.examples.resources.journal.ArticlesRes;
import org.univaq.swa.examples.resources.journal.IssuesRes;
import org.univaq.swa.examples.resources.Resource1;
import org.univaq.swa.examples.resources.Resource2;
import org.univaq.swa.examples.security.AuthLevel1Filter;
import org.univaq.swa.examples.security.AuthenticationRes;

/**
 *
 * @author SviluppoWebAvanzato
 *
 * "rest" sarà il path di base a cui risponderà il nostro servizio
 *
 */
@ApplicationPath("rest")
public class JAXRSApplication extends Application {
    private final Set<Class<?>> classes;
    public JAXRSApplication() {
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        //aggiungiamo tutte le *root resurces* (cioè quelle
        //con l'annotazione Path) che vogliamo pubblicare
        c.add(Resource1.class);
        //notare come questo esempio presenti più risorse root
        c.add(Resource2.class);
        c.add(AuthenticationRes.class);
        c.add(ArticlesRes.class);
        c.add(IssuesRes.class);

        //aggiungiamo il provider Jackson per poter
        //usare i suoi servizi di serializzazione e 
        //deserializzazione JSON
        c.add(JacksonJsonProvider.class);

        //necessario se vogliamo una (de)serializzazione custom di qualche classe    
        c.add(ObjectMapperContextResolver.class);

        //esempio di autenticazione
        c.add(AuthLevel1Filter.class);

        //aggiungiamo il filtro che gestisce gli header CORS
        c.add(CORSFilter.class);

        //esempio di exception mapper, che mappa in Response eccezioni non già derivanti da WebApplicationException
        c.add(AppExceptionMapper.class);
        
        //swagger
        https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-Configuration#jersey-jax-rs-container-servlet
        c.add(OpenApiResource.class);
        c.add(AcceptHeaderOpenApiResource.class);



        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
