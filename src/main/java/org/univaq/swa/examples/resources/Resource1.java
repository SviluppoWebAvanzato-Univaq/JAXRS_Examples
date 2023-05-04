package org.univaq.swa.examples.resources;

import org.univaq.swa.examples.model.AdvancedClass;
import org.univaq.swa.examples.model.SimpleClass;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.univaq.swa.examples.exceptions.RESTWebApplicationException;
import org.univaq.swa.examples.security.AuthLevel1;

/**
 *
 * @author SviluppoWebAvanzato
 */

/*
 * l'annotazione @Path sulla classe la definisce come
 * root resource, che sarà pubblicata dalla servlet
 * di JAX-RS immediatamente sotto il suo URL pattern
 * quindi nel nostro caso /rest/res1
 */
@Path("res1")
public class Resource1 {

    /*
     * i metodi non marcati con annotazioni JAX-RS restano interni alla classe
     * e non sono esposti tramite l'API RESTful
     */
    public int pippo() {
        return 0;
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// GET ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * l'annotazione @GET indica a JAX-RS che 
     * questo metodo verrà chimato quando si richiederà
     * la risorsa-classe (cioè /rest/res1) con il verbo
     * HTTP GET
     * l'annotazione @Produces indica a JAX-RS che, in 
     * particolare, il metodo verrà chiamato se si richiede
     * (header Accept della richiesta) una risposta in formato
     * "text/plain"
     */
    //GET /rest/res1 
    //Accept: text/plain
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get_text() {
        /*
         * JAX-RS trasforma la stringa restituita nel contenuto
         * della risposta HTTP
         */
        return "ciao";
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * stesso verbo HTTP sulla stessa risprsa,
     * ma in questo caso il metodo verrà chiamato
     * se la richiesta indica JSON come tipo preferito
     * per il formato di ritorno. JAX-RS proverà a convertire
     * il dato restituito dal metodo nel tipo dichiarato utilizzando
     * i provider noti (nel nostro caso Jackson)
     * Inoltre, invece di restituire direttamente il dato
     * (una stringa in questo caso), usiamo la classe
     * Response di JAX-RS. Questa ci fornisce maggiore
     * versatilità, ad esempio nel codice HTTP da restituire
     * assieme al contenuto.
     */
    //GET /rest/res1
    //Accept: application/json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_json() {
        /*
         * impostiamo il codice 200 (OK),
         * alleghiamo i dati da trasmettere e generiamo
         * la response  da restituire
         */
        //equivalente a return "ciao" con return type String
        return Response.ok("ciao").build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /* 
     * Tramite l'annotazione @Path definiamo un sotto-path
     * a cui questo metodo dovrà rispondere, in particolare
     * se si invia una richiesta GET con tipo di ritorno
     * application/json sul path /rest/res1/list
     * Notare come JAX-RS, tramite Jackson, converte correttamente
     * la List di Java in una lista JSON.
     */
    //GET /rest/res1/list
    //Accept: application/json
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_list_json() {
        List<String> l = new ArrayList<>();
        l.add("A");
        l.add("B");
        return Response.ok(l).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * restituendo un oggetto che implementa 
     * StreamingOutput possiamo scrivere la risposta
     * su uno stream, quindi trasmettere anche contenuti
     * di grosse dimensioni, come un binario
     */
    //GET /rest/res1/binary
    //Accept: application/octet-stream
    @GET
    @Path("binary")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response get_binary_json() {
        final byte[] dummyData = new byte[1024];
        StreamingOutput result = new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException {
                out.write(dummyData);
            }
        };
        //notare come incapsuliamo lo StreamingOutput nella Result,
        //aggiungendo anche un header che facilita il download del
        //contenuto come file...
        return Response.ok(result).header("content-disposition", "attachment; filename=prova.txt").build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * vediamo ora come è possibile restituire direttamente
     * delle strutture dati Java (dei bean, precisamente)
     * lasciando a JAX-RS l'onere di convertirle nel formato
     * dati richiesto per l'output.
     * Questo metodo verrà chiamato se il client effettua
     * una GET sulla url /rest/res1/bean chiedendo
     * output di tipo JSON
     */
    //GET /rest/res1/bean
    //Accept: application/json
    @GET
    @Path("bean")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_bean_json() {
        // Creiamo una classe dati giocattolo 
        SimpleClass c = new SimpleClass("class1", 1, new SimpleClass("class2", 3, null));
        //JAX-RS la serializza automaticamente in JSON se Jackson è tra le librerie!
        return Response.ok(c).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    //GET /rest/res1/beanlist
    //Accept: application/json
    @GET
    @Path("beanlist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_bean_list_json() {
        List<SimpleClass> l = new ArrayList<>();
        l.add(new SimpleClass("class1", 1, new SimpleClass("class2", 3, null)));
        l.add(new SimpleClass("class3", 4, new SimpleClass("class4", 5, null)));
        return Response.ok(l).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    //GET /rest/res1/map
    //Accept: application/json
    @GET
    @Path("map")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_map_json() {
        Map<String, Object> m = new HashMap<>();
        m.put("Numero", 1);
        m.put("Oggetto", new SimpleClass("s", 0, null));
        return Response.ok(m).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * E' possibile definire dei custom (de)serializers
     * per personalizzare il modo in cui le strutture verranno
     * (de)serializzate in JSON
     */
    //GET /rest/res1/custombean
    //Accept: application/json
    @GET
    @Path("custombean")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_custombean_json() {
        // Creiamo una classe dati giocattolo 
        AdvancedClass c = new AdvancedClass("class1", 1, new AdvancedClass("class2", 3, null));
        return Response.ok(c).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * proviamo ora a gestire path parametrici, del tipo
     * collezione/ID-elemento. In paticolare, scriviamo
     * un @Path che contiene un parametro chiamato "id"
     * definito da un'espressione regolare.
     * Questo metodo verrà quindi chiamato se si invia
     * una righiesta GET con tipo di ritorno application/json
     * su URL del tipo /rest/res1/pippo/<numero>
     */
    //GET /rest/res1/items/<numero>
    //Accept: application/json
    @GET
    @Path("items/{id: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_item_by_id(@PathParam("id") int n) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok("Item " + n).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * in questo esempio mostriamo come iniettare tra i
     * parametri di un metodo il valore di un campo inserito
     * nella stringa di query della URL. Questo metodo verrà
     * chiamato quindi con url del tipo
     * /rest/res1/pippo?filtro=valore
     */
    //GET /rest/res1/items?filter=x
    //Accept: application/json
    @GET
    @Path("items")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get_filtered_collection(@QueryParam("filter") String f) {
        /*
         * L'annotazione @QueryParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della query string col nome indicato. JAX-RS proverà
         * a convertire il parametro della query string nel tipo richiesto
         * dal metodo. Se il parametro non è specificato, verrà impostato
         * su null.
         */
        if (f != null) {
            return Response.ok(f).build();
        } else {
            return Response.serverError().build();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// POST ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * Vediamo ora come rispondere alle richieste che
     * contengono un payload, come una POST.
     * Come indicato dall'annotazione @POST sul metodo,
     * questo metodo verrà chiamato se si effettua una
     * POST sulla risorsa /rest/res1 (visto che non
     * c'è alcuna annotazione @Path) inviando dei dati in
     * formato JSON, come indicato dall'annotazione
     * @Consumes
     */
    //POST /rest/res1
    //Content-Type: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_item_string(
            //tramite l'annotazione @Context, iniettiamo
            //tra i parametri un utile oggetto fornito da
            //JAX-RS
            @Context UriInfo c,
            //il parametro senza annotazioni viene invece
            //riempito da JAX-RS col payload della richiesta
            String data) {

        /* 
         * Qui dovremmo interpretare il payload, magari
         * parsandolo in un JSONObject, e usarlo, probabilmente
         * per effettuare un inserimento dati. Alla fine, restituiremo
         * la URL per accedere all'elemento appena inserito.
         *
         * Un modo conveniente (ma non imposto) per creare
         * la URI da restituire è usare l'UriInfo iniettato
         * tra i parametri. Qui chiediamo che sia costruita
         * la URI necessaria ad attivare questa classe e poi chiamarne
         * il metodo get_pippo_item_by.
         * Visto che get_item_by_id è annotato con un @Path 
         * parametrico, passiamo a build il valore per questo
         * parametro, che dovrebbe essere la chiave dell'elemento
         * appena creato...
         */
        URI u = c.getBaseUriBuilder()
                .path(Resource1.class) //arriviamo alla risorsa Resource1
                .path(Resource1.class, "get_item_by_id") //passiamo al metodo specifico che ci interessa
                .build(4);
        /* se invece si volesse costruire il path verso una sotto-risorsa
         * si dovrebbe procedere inserendo uno a uno i segmenti di path che portano
         * ad essa, concludendo con il path verso il metodo finale, ad esempio
         */
//      URI u2 = c.getBaseUriBuilder()
//             .path(Resource1.class) //arriviamo alla risorsa Resource1
//             .path(Resource1.class,"toSub1") //passiamo alla Subresource1 tramite il suo metodo toSub1
//             .path(Subresource1.class, "get_json") //chiamiamo uno specifico metodo della Subresource1 
//             .build()); //supponiamo che nel path costruito non ci siano parametri

        /* ATTENZIONE: la sintassi alternativa sotto riportata, invece, APPENDE il path
         * per il metodo get_item_by_id al path assoluto della CHIAMATA
         * corrente (non della risorsa Resource1!).
         */
//        URI u = c.getAbsolutePathBuilder()
//                .path(Resource1.class, "get_item_by_id")
//                .build(4);
        /* 
         * o alternativamente possiamo costruire la URI "a mano" 
         */
//        u = new URI("http://pippo.com/uri/restituita");
        return Response.created(u).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * Stesso esempio del metodo precedente, ma qui rispondiamo   
     * al POST sulla risorsa /rest/res1/stream
     */
    //POST /rest/res1/stream
    //Content-Type: application/json
    @POST
    @Path("stream")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_item_stream(
            @Context UriInfo c,
            // Possiamo anche (per POST e PUT) dire a JAX-RS
            // di fornirci il payload sotto forma ti uno stream.
            // (utile spprattutto per payload lunghi o binari)
            InputStream data) {

        /* 
         * Qui dovremmo leggere il payload dallo stream
         */
        URI u = c.getBaseUriBuilder()
                .path(Resource1.class)
                .path(Resource1.class, "get_item_by_id")
                .build(4);

        return Response.created(u).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * Stesso esempio del metodo precedente, ma qui rispondiamo   
     * al POST sulla risorsa /rest/res1/bean
     */
    //POST /rest/res1/bean
    //Content-Type: application/json
    @POST
    @Path("bean")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_item_bean(
            @Context UriInfo c,
            // Possiamo anche (per POST e PUT) dire a JAX-RS
            // di decodificare automaticamente il payload in un
            // oggetto, se compatibile.
            SimpleClass data) {

        /* 
         * Attenzione: per poter essere deserializzato l'oggetto
         * deve essere dotato di un construttore di default 
         * (senza parametri), oltre ovviamente ad avere campo
         * mappabil su quelli del JSON del payload.
         */
        URI u = c.getBaseUriBuilder()
                .path(Resource1.class)
                .path(Resource1.class, "get_item_by_id")
                .build(4);

        return Response.created(u).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * Stesso esempio del metodo precedente, ma qui rispondiamo   
     * al POST sulla risorsa /rest/res1/beanlist e decodifichiamo
     * una lista di classi
     */
    //POST /rest/res1/beanlist
    //Content-Type: application/json
    @POST
    @Path("beanlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_item_beanlist(
            @Context UriInfo c,
            List<SimpleClass> data) {

        URI u = c.getBaseUriBuilder()
                .path(Resource1.class)
                .path(Resource1.class, "get_item_by_id")
                .build(5);

        return Response.created(u).build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * In questo caso il metodo POST accetta più tipi diversi come payload.
     * Non potendo eseguire una decodifica immediata, riceviamo il payload come 
     * stringa e poi decidiamo in base al tipo effettivo come trattarlo. 
     * Da notare l'uso dell'annotazione @Context qui per iniettare un altro elemento
     * del contesto JAX-RS: gli header della richiesta.
     */
    //POST /rest/res1/multitype
    //Content-Type: application/json o text/plain
    @POST
    @Path("multitype")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response add_item_mixed(
            @Context UriInfo c, @Context HttpHeaders headers, String payload) {
        MediaType mediaType = headers.getMediaType();

        UriBuilder ub = c.getBaseUriBuilder()
                .path(Resource1.class)
                .path(Resource1.class, "get_item_by_id"); //passiamo alla Subresource1 tramite il suo metodo toSub1     

        if (null != mediaType) {
            switch (mediaType.toString()) {
                case MediaType.APPLICATION_JSON:
                    return Response.created(ub.build(8)).build();
                case MediaType.TEXT_PLAIN:
                    return Response.created(ub.build(7)).build();
            }
        }
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();

    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// PUT ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * Quanto detto per POST vale anche per il metodo PUT.
     */
    //PUT /rest/res1/custombean
    //Content-Type: application/json
    @PUT
    @Path("custombean")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put_item_bean(AdvancedClass c) {
        //di solito una PUT restituisce NO CONTENT
        return Response.noContent().build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// DELETE /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * Rispondere alla delete è semplice: basta usare 
     * l'annotazione @DELETE sul metodo. Si possono eventualmente
     * usare sempre le annotazioni @Path e @PathParam
     * per fare in modo che il metodo risponda a sotto URL
     *
     */
    //DELETE /rest/res1
    @DELETE
    public Response delete() {
        return Response.noContent().build();
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// SOTTO RISORSE //////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * un metodo con annotazione @Path ma senza verbo HTTP
     * e senza @Produces/@Consumes fa sì che JAX-RS passi il controllo
     * alla classe-risorsa restituita dal metodo. La scansione della URL
     * e la ricerca del metodo da chiamare continueranno quindi nell'oggetto
     * restituito
     */
    //<qualsiasi metodo> /rest/res1/sub1
    @Path("sub1")
    public Subresource1 toSub1() {
        return new Subresource1();
    }

    ////////////////////////////////////////////////////////////////////////////
    //<qualsiasi metodo> /rest/res1/sub2/<n>
    @Path("sub2/{n: [0-9]+}")
    public Subresource2 toSub2() {
        return new Subresource2();
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * Possiamo ovviamente configurare l'oggetto restituito. In questo modo,
     * è anche possibile riusare lo stesso oggetto su path diversi: basterà
     * renderlo "cosciente" del path che lo ha attivato usando i parametri passati,
     * ad esempio, al costruttore.
     */
    //<qualsiasi metodo> /rest/res1/sub3/<n>
    @Path("sub3/{n: [0-9]+}")
    public Subresource3 toSub3(@PathParam("n") int n) {
        return new Subresource3(n);
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     * La sotto-risorsa che segue può essere attivata anche come risorsa radice,
     * con il path rest/res1sub4
     */
    //<qualsiasi metodo> /rest/res1/res2
    @Path("res2")
    public Resource2 toRes2() {
        return new Resource2(3);
    }

    ////////////////////////////////////////////////////////////////////////////
    /* 
     * E' possibile restituire oggetti generici come sotto-ricorse, variando
     * quello effettivamente restituito in base a dei criteri. JAX-RS analizzerà
     * l'oggetto restituito "al volo" per gestirne le operazioni!
     */
    //<qualsiasi metodo> /rest/res1/sub5/<n>
    @Path("sub5/{n: [0-9]+}")
    public Object toSub5(@PathParam("n") int n) {
        switch (n) {
            case 1:
                return new Subresource1();
            case 2:
                return new Subresource2();
            default:
                return new Subresource3(90);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /* 
     * In caso di errori, è possibile sollevare eccezioni derivate dalla
     * CustomWebApplicationException, che permettono di restituire al browser
     * uno stato HTTP e un messaggio.
     */
    //GET /rest/res1/sub6
    @Path("sub6")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response toSub6() {
        throw new RESTWebApplicationException(500, "problema");
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////// SICUREZZA //////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /*
     * questa risorsa non verrà restituita se non si ha un corretto bearer token
     * associato alla richiesta
     */
    //<qualsiasi metodo> /rest/res1/secure
    @Path("secure")
    @GET
    @AuthLevel1
    public String get_secures_text() {
        return "secured text";
    }
}
