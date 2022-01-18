package be.kuleuven.pccustomizer.controller;

import be.kuleuven.pccustomizer.controller.Objects.Bestelling;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

public class CouchClient {

    public CouchClient() {}

    public void query(Bestelling bestelling) {
        var dbClient = new CouchDbClient();
        Response response = dbClient.save(bestelling);
    }
}
