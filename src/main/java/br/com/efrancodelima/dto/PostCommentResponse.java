package br.com.efrancodelima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCommentResponse {
    private Post post;
    private Comment comments;
}
