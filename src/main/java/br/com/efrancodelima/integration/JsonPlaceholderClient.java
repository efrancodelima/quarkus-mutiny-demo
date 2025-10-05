package br.com.efrancodelima.integration;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.efrancodelima.dto.Comment;
import br.com.efrancodelima.dto.Post;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey  = "jsonplaceholder")
public interface JsonPlaceholderClient {

    @GET
    @Path("/posts/{id}")
    Uni<Post> getPost(@PathParam("id") int postId);

    @GET
    @Path("/comments/{id}")
    Uni<Comment> getComment(@PathParam("id") int commentId);
}
