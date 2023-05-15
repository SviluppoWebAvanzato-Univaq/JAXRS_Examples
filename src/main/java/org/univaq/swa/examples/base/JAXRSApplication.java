package org.univaq.swa.examples.base;

import org.univaq.swa.framework.security.CORSFilter;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import org.univaq.swa.framework.jackson.ObjectMapperContextResolver;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.univaq.swa.examples.exceptions.AppExceptionMapper;
import org.univaq.swa.examples.exceptions.JacksonExceptionMapper;
import org.univaq.swa.examples.resources.journal.ArticlesRes;
import org.univaq.swa.examples.resources.journal.IssuesRes;
import org.univaq.swa.examples.resources.Resource1;
import org.univaq.swa.examples.resources.Resource2;
import org.univaq.swa.framework.security.AuthLoggedFilter;
import org.univaq.swa.framework.security.AuthenticationRes;

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
        c.add(ArticlesRes.class);
        c.add(IssuesRes.class);
        c.add(AuthenticationRes.class);

        //aggiungiamo il provider Jackson per poter
        //usare i suoi servizi di serializzazione e 
        //deserializzazione JSON
        c.add(JacksonJsonProvider.class);

        //necessario se vogliamo una (de)serializzazione custom di qualche classe    
        c.add(ObjectMapperContextResolver.class);

        //esempio di autenticazione
        c.add(AuthLoggedFilter.class);

        //aggiungiamo il filtro che gestisce gli header CORS
        c.add(CORSFilter.class);

        //esempi di exception mapper, che mappano in Response eccezioni non già derivanti da WebApplicationException
        c.add(AppExceptionMapper.class);
        c.add(JacksonExceptionMapper.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
