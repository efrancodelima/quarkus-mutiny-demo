package br.com.efrancodelima.integration;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.efrancodelima.model.CatFact;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey  = "catfact")
public interface CatFactsClient {
    @GET
    @Path("/fact")
    Uni<CatFact> getCatFact();
}
