package org.univaq.swa.examples.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Questa classe è contemporaneamente una sotto-risorsa e una risorsa radice.
 * Come sotto-risorsa, viene attivata tramite il passaggio di controllo da parte
 * di un'altra risorsa. In questo caso l'annotazione @Path viene ignorata.
 * Essendo anche registrata come risorsa radice nella classe JAXRSApplication,
 * potrà essere attivata anche tramite il suo @Path, cioè scrivendo rest/res4 In
 * questo caso, accertarsi che disponga (anche) di un costrutture di default!
 *
 * @author Giuseppe Della Penna
 */
@Path("res2")
public class Resource2 {

    private final int i;

    public Resource2() {
        this(0);
    }

    public Resource2(int i) {
        this.i = i;
    }

    //GET /rest/res1/sub4 o /rest/res2
    //Accept: application/json
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json() {
        return Response.ok("greetings from Resource2. i=" + i).build();
    }
}
