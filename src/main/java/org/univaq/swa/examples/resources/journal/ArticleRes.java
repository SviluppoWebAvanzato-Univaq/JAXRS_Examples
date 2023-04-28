package org.univaq.swa.examples.resources.journal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



public class ArticleRes {

    /*
     * questa sub-resource riceve tutti i dati necessari
     * alla sua istanziazione direttamente nel costruttore.
     * In questo modo è totalmente indipendente dal path che
     * ha portato alla sua attivazione, in particolare i parametri
     * che la definiscono (qui solo aid) possono provenire dal path
     * o da altre sorgenti
     */
    private final int aid;

    public ArticleRes(int aid) {
        this.aid = aid;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_json() {
        return Response.ok("greetings from article " + aid).build();
    }

    /*
     * questo metodo può essere attivato da molte URL, data la ricorsività
     * che abbiamo creato nel codice, ad esempio
     * rest/articles/<numero1>/issue
     * rest/articles/<numero1>/issue/articles/<numero2>/issue
     * rest/issues/<numero1>/articles/<numero2>/issue
     * ...
    */
    @Path("issue")
    public IssueRes getIssue() {
        return new IssueRes(aid + 10);
    }
}
