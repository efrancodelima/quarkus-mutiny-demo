package br.com.efrancodelima.resource;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import br.com.efrancodelima.api.ConsultarCatFactApi;
import br.com.efrancodelima.service.ConsultarCatFactService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ConsultarCatFactResource implements ConsultarCatFactApi {

    @Inject
    private ConsultarCatFactService service;

    @Override
    public Uni<List<String>> consultar(Integer numeroRequisicoes) {

        Instant inicio = Instant.now();

        Uni<List<String>> resposta = service.consultar(numeroRequisicoes);

        resposta.subscribe().with(r -> {
            Instant fim = Instant.now();
            Duration duracao = Duration.between(inicio, fim);
            System.out.printf("Tempo de resposta %d ms %n", duracao.toMillis());
        });

        return resposta;
    }
}
