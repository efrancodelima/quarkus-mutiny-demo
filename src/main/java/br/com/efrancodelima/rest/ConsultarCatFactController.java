package br.com.efrancodelima.rest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.efrancodelima.model.CatFact;
import br.com.efrancodelima.service.ConsultarCatFactService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("/testar")
@Tag(name = "Testar", description = "Endpoint de teste da API")
public class ConsultarCatFactController {

  @Inject
  private ConsultarCatFactService service;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
    summary = "Retorna 20 fatos sobre gatos",
    description = "Dispara 20 requisições externas assíncronas usando Mutiny.")
  @APIResponse(
      responseCode = "200",
      description = "Resposta bem-sucedida",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = CatFact.class)
      )
  )
  public Uni<List<CatFact>> consultar() {

    Instant inicio = Instant.now();

    Uni<List<CatFact>> resposta = service.testarAsync();

    resposta.subscribe().with(r -> {
      Instant fim = Instant.now();
      Duration duracao = Duration.between(inicio, fim);
      System.out.printf("Tempo de resposta %d ms %n", duracao.toMillis());
    });

    return resposta;
  }
}
