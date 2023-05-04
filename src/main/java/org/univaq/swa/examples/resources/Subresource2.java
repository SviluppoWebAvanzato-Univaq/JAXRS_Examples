package org.univaq.swa.examples.resources;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Questa è una sotto-risorsa, in quanto non ha un'annotazione @Path e quindi
 * può essere attivata solo tramite il passaggio di controllo da parte di
 * un'altra risorsa. L'elaborazione del path continuerà qui, senza considerare
 * il prefisso già consumato dalla risorsa padre.
 *
 * @author Giuseppe Della Penna
 */
public class Subresource2 {

    //GET /rest/res1/sub2/<n>
    //Accept: application/json
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json(@PathParam("n") int n) {
        //notare come possiamo iniettare nel metodo anche parametri non compresi nel Path che lo annota 
        //(che qui non è presente): in tal caso JAX-RS cercherà questo parametro tra quelli già isolati dalle
        //risorse che hanno attivato questa sotto-risorsa (vedi metodo toSub2 in Resource1)        
        return Response.ok("greetings from Subresource2 number " + n).build();
    }

    /* 
     * una sotto risorsa può fare tutto quello che fa una risorsa radice
     * ad esempio gestire direttamente dei sotto-path...
     */
    //GET /rest/res1/sub2/<n>/hello
    //Accept: application/json
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_pippo_json() {
        return Response.ok("greetings!").build();
    }

    /* 
     * ...oppure gestire metodi diversi...
     */
    //DELETE /rest/res1/sub2/<n>
    @DELETE
    public Response delete() {
        return Response.noContent().build();
    }

    /* 
     * ...oppure avere altre sotto-risorse!
     */
    //<qualsiasi metodo> /rest/res1/sub2/<n>/sub
    @Path("sub")
    public Subresource4 toSub4() {
        return new Subresource4();
    }

}
