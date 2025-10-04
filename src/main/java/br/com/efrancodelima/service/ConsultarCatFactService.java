package br.com.efrancodelima.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.efrancodelima.integration.CatFactsClient;
import br.com.efrancodelima.model.CatFact;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ConsultarCatFactService {

  @Inject
  @RestClient
  private CatFactsClient catFactsService;

  /**
   * Consome um serviço externo disparando 20 requisições de forma
   * reativa e assíncrona, ou seja, elas são disparadas em paralelo
   * e não em série.
   */
  public Uni<List<CatFact>> testarAsync() {

    final int QTDE_CAT_FACTS = 20;

    List<Uni<CatFact>> chamadas = new ArrayList<>();

    for (int i = 0; i < QTDE_CAT_FACTS; i++) {
      chamadas.add(catFactsService.getCatFact());
    }

    return Uni.combine().all().unis(chamadas)
      .with(
        results -> results.stream()
          .map(result -> (CatFact) result)
          .collect(Collectors.toList())
      );
  }
}
