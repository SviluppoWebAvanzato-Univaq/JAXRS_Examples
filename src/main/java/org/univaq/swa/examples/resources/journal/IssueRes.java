package org.univaq.swa.examples.resources.journal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class IssueRes {

    /*
     * questa sub-resource riceve tutti i dati necessari
     * alla sua istanziazione direttamente nel costruttore.
     * In questo modo è totalmente indipendente dal path che
     * ha portato alla sua attivazione, in particolare i parametri
     * che la definiscono (qui solo iid) possono provenire dal path
     * o da altre sorgenti
     */
    private final int iid;

    public IssueRes(int iid) {
        this.iid = iid;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json() {
        return Response.ok("greetings from issue " + iid).build();
    }

    /*
     * questo metodo può essere attivato da molte URL, data la ricorsività
     * che abbiamo creato nel codice, ad esempio
     * rest/issues/<numero1>/articles
     * rest/issues/<numero1>/articles/<numero2>/issue/articles
     * rest/articles<numero2>/issue/articles
     * rest/articles<numero2>/issue/articles/<numero3>/issue/articles
     * ...
     */
    @Path("articles")
    public ArticlesRes getAticles() {
        //notare come restituiamo come sub-resource una root resource
        //(cioè dotata della sua annotazione @Path). In tal caso l'annotazione
        //presente sulla classe ArticlesRes non viene considerata, e la classe
        //così costruita, grazie al parametro, si comporterà come la lista
        //di tutti gli ariticoli relativi a questo issue
        return new ArticlesRes(iid);
    }
}
