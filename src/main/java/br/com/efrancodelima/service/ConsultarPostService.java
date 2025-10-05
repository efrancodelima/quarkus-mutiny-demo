package br.com.efrancodelima.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.efrancodelima.dto.Comment;
import br.com.efrancodelima.dto.Post;
import br.com.efrancodelima.dto.PostCommentRequest;
import br.com.efrancodelima.dto.PostCommentResponse;
import br.com.efrancodelima.integration.JsonPlaceholderClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ConsultarPostService {

    @Inject
    @RestClient
    private JsonPlaceholderClient jsonPlaceholderClient;

    /**
     * Consome dois serviços externos disparando duas requisições de 
     * forma assíncrona, ou seja, elas são disparadas em paralelo
     * e não em série.
     */
    public Uni<PostCommentResponse> consultar(PostCommentRequest requisicao) {

        Uni<Post> post = jsonPlaceholderClient.getPost(requisicao.getPostId());
        Uni<Comment> comment = jsonPlaceholderClient.getComment(requisicao.getCommentId());

        prepararTratamentoErros(post, comment);

        return processarChamadas(post, comment);
    }

    // Métodos privados
    private void prepararTratamentoErros(Uni<Post> post, Uni<Comment> comment) {

        post = post
            .onFailure().invoke(error -> 
                System.out.println("Erro: " + error.getMessage())
            )
            .onFailure().recoverWithItem(
                new Post(0,0, "Erro", "Erro ao consultar o post!")
            );

        comment = comment
            .onFailure().invoke(error -> 
                System.out.println("Erro: " + error.getMessage())
            )
            .onFailure().recoverWithItem(
                new Comment(0,0, "", "", "Erro ao consultar o comentário!")
            );
    }

    private Uni<PostCommentResponse> processarChamadas(Uni<Post> post, Uni<Comment> comment) {
        return Uni.combine().all().unis(post, comment).asTuple()
            .map(tuple -> {
                return new PostCommentResponse(tuple.getItem1(), tuple.getItem2());
            });
    }
}
