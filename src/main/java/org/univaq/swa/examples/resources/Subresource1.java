package org.univaq.swa.examples.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Questa è una sotto-risorsa, in quanto non ha un'annotazione @Path e quindi
 * può essere attivata solo tramite il passaggio di controllo da parte di
 * un'altra risorsa. L'elaborazione del path continuerà qui, senza considerare i
 * prefisso già consumato dalla risorsa padre.
 *
 * @author Giuseppe Della Penna
 */
public class Subresource1 {
    //GET /rest/res1/sub1
    //Accept: application/json
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json() {
        return Response.ok("greetings from Subresource1").build();
    }
}
