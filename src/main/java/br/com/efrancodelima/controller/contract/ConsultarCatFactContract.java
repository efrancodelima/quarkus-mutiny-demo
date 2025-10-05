package br.com.efrancodelima.controller.contract;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/catfact")
@Tag(
    name = "API da aplicação",
    description = "API contendo endpoints que realizam operações assíncronas usando Quarkus e Mutiny."
)
public interface ConsultarCatFactContract {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Retorna fatos aleatórios sobre gatos",
        description = "Dispara requisições assíncronas para um único serviço externo."
    )
    @APIResponse(
        responseCode = "200",
        description = "Resposta bem-sucedida",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = String.class)
        )
    )
    Uni<List<String>> consultar(
        @Parameter(
            description = "Número de requisições a serem feitas",
            required = true,
            example = "5"
        )
        @QueryParam("numeroRequisicoes")
        @Min(1) @Max(50)
        Integer numeroRequisicoes
    );
}

