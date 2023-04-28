package org.univaq.swa.examples.resources.journal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SviluppoWebAvanzato
 */
@Path("articles")
public class ArticlesRes {

    /*
     * Questa root resource può essere utilizzata anche come sub-resource
     * in base al parametro passato nel costruttore.
     * Se il parametro viene omesso, si tratterà di una risoursa root che
     * rappresenta tutti gli articoli, altrimenti sarà una sub-resource che
     * rappresenta i soli articoli dell'issue identificato dal parametro stesso.
     */
    private int iid = 0;

    public ArticlesRes(int iid) {
        this.iid = iid;
    }

    public ArticlesRes() {
        this.iid = 0;
    }

    /*
     * questo metodo può essere attivato da molte URL, data la ricorsività
     * che abbiamo creato nel codice, ad esempio
     * rest/articles 
     * rest/issues/<numero1>/articles
     * rest/issues/<numero1>/articles/<numero2>/issue/articles
     * rest/articles<numero2>/issue/articles
     * rest/articles<numero2>/issue/articles/<numero3>/issue/articles
     * ...
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_json(@Context UriInfo uriinfo) {
        List<String> l = new ArrayList<>();
        l.add(uriinfo.getBaseUriBuilder().path(ArticlesRes.class).path(ArticlesRes.class, "getArticle").build(5).toString());
        l.add(uriinfo.getBaseUriBuilder().path(ArticlesRes.class).path(ArticlesRes.class, "getArticle").build(6).toString());
        if (iid > 0) {
            l.add(uriinfo.getBaseUriBuilder().path(ArticlesRes.class).path(ArticlesRes.class, "getArticle").build(iid).toString());
        }
        return Response.ok(l).build();
    }

    @Path("{aid: [0-9]+}")
    public ArticleRes getArticle(@PathParam("aid") int aid) {
        //notare che costruiamo la risorsa passandole nel costruttore il numero di 
        //article da rappresentare, e non facendo prelevare questo numero dal path 
        //dalla risorsa stessa. Questo rende la ArticleRes più indipendente dal path,
        //come vedremo
        return new ArticleRes(aid);
    }
}
