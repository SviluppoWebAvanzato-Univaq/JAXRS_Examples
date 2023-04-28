package org.univaq.swa.examples.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
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
public class Subresource3 {

    private final int i;

    public Subresource3(int i) {
        this.i = i;
    }

    /*
     * Se l'oggetto prova ad accedere a una PathParam non presente
     * nel path effettivo che ha portato alla sua attivazione,
     * il parametro sarà posto a nullo (o zero in questo caso):
     * provate a chiamare /rest/res1/sub3 e /rest/res1/sub3/9
     */
    //GET /rest/res1/sub3/<n>}
    //Accept: application/json
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json(@PathParam("other") int other) {
        return Response.ok("greetings from Subresource3. i=" + i + ", other=" + other).build();
    }
}
