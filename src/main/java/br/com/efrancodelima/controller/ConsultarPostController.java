package br.com.efrancodelima.controller;

import java.time.Duration;
import java.time.Instant;

import br.com.efrancodelima.controller.contract.ConsultarPostContract;
import br.com.efrancodelima.dto.PostCommentRequest;
import br.com.efrancodelima.dto.PostCommentResponse;
import br.com.efrancodelima.service.ConsultarPostService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@RequestScoped
public class ConsultarPostController implements ConsultarPostContract {

    @Inject
    ConsultarPostService service;

    @Override
    public Uni<PostCommentResponse> consultar(@Valid PostCommentRequest requisicao) {
        Instant inicio = Instant.now();

        Uni<PostCommentResponse> resposta = service.consultar(requisicao);

        resposta.subscribe().with(r -> {
            Instant fim = Instant.now();
            Duration duracao = Duration.between(inicio, fim);
            System.out.printf("Tempo de resposta %d ms %n", duracao.toMillis());
        });

        return resposta;
    }
}
