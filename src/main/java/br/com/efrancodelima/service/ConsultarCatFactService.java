package br.com.efrancodelima.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.efrancodelima.dto.CatFact;
import br.com.efrancodelima.integration.CatFactsClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ConsultarCatFactService {

    @Inject
    @RestClient
    private CatFactsClient catFactsClient;

    /**
     * Consome um serviço externo disparando várias requisições de 
     * forma assíncrona, ou seja, elas são disparadas em paralelo
     * e não em série.
     */
    public Uni<List<String>> consultar(Integer numeroRequisicoes) {

        List<Uni<CatFact>> chamadasExternas = criarChamadasExternas(numeroRequisicoes);

        prepararTratamentoErros(chamadasExternas);

        return processarChamadas(chamadasExternas);
    }

    // Métodos privados
    private List<Uni<CatFact>> criarChamadasExternas(int numeroRequisicoes) {
        List<Uni<CatFact>> chamadasExternas = new ArrayList<>();
        for (int i = 0; i < numeroRequisicoes; i++) {
            chamadasExternas.add(catFactsClient.getCatFact());
        }
        return chamadasExternas;
    }

    private void prepararTratamentoErros(List<Uni<CatFact>> chamadasExternas) {
        chamadasExternas = chamadasExternas.stream().map(
            chamada -> chamada
                .onFailure().invoke(
                    error -> System.out.println("Erro: " + error.getMessage())
                )
                .onFailure().retry().atMost(2)
                .onFailure().recoverWithItem((CatFact) null)
            )
            .collect(Collectors.toList());
    }

    private Uni<List<String>> processarChamadas(List<Uni<CatFact>> chamadasExternas) {
        return Uni.combine().all()
            .unis(chamadasExternas)
            .with(
                results -> results.stream()
                    .filter(Objects::nonNull)
                    .map(result -> ((CatFact) result).getFact())
                    .collect(Collectors.toList())
            );
    }
}
