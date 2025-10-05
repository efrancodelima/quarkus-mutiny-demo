package br.com.efrancodelima.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCommentRequest {

    @NotNull(message = "O número do post é obrigatório.")
    @Min(value = 1, message = "O número do post deve ser igual ou maior que 1.")
    @Max(value = 100, message = "O número do post deve ser igual ou menor que 100.")
    private Integer postId;

    @NotNull(message = "O número do comentário é obrigatório.")
    @Min(value = 1, message = "O número do comentário deve ser igual ou maior que 1.")
    @Max(value = 500, message = "O número do comentário deve ser igual ou menor que 500.")
    private Integer commentId;
}
