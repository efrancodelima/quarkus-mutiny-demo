package br.com.efrancodelima.api;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.efrancodelima.dto.PostCommentRequest;
import br.com.efrancodelima.dto.PostCommentResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/post")
@Tag(
    name = "API da aplicação",
    description = "API contendo endpoints que realizam operações assíncronas usando Quarkus e Mutiny."
)
public interface ConsultarPostApi {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Retorna dados de um post e um comentário aleatórios.",
        description = "Dispara requisições assíncronas para 2 serviços externos diferentes."
    )
    @APIResponse(
        responseCode = "200",
        description = "Resposta bem-sucedida",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PostCommentResponse.class)
        )
    )
    Uni<PostCommentResponse> consultar(@Valid PostCommentRequest requisicao);
}
