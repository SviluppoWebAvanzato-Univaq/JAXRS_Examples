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
@Path("issues")
public class IssuesRes {

    //GET /rest/issues 
    //Accept: application/json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_json(@Context UriInfo uriinfo) {
        List<String> l = new ArrayList<>();

        l.add(uriinfo.getBaseUriBuilder().path(IssuesRes.class).path(IssuesRes.class, "getIssue").build(1).toString());
        l.add(uriinfo.getBaseUriBuilder().path(IssuesRes.class).path(IssuesRes.class, "getIssue").build(2).toString());
        return Response.ok(l).build();
    }

    //<qualsiasi metodo> /rest/issues/<numero>
    @Path("{iid: [0-9]+}")
    public IssueRes getIssue(@PathParam("iid") int iid) {
        //notare che costruiamo la risorsa passandole nel costruttore il numero di 
        //issue da rappresentare, e non facendo prelevare questo numero dal path 
        //dalla risorsa stessa. Questo rende la IssueRes più indipendente dal path,
        //come vedremo
        return new IssueRes(iid);
    }
}
