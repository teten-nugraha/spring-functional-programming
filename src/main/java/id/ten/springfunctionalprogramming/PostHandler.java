package id.ten.springfunctionalprogramming;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;

import static org.springframework.web.servlet.function.ServerResponse.*;

@Component
public class PostHandler {

    private final PostRepository postRepository;

    public PostHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ServerResponse all(ServerRequest req) {
        return ok().body(this.postRepository.findAll());
    }

    public ServerResponse create(ServerRequest req) throws ServletException, IOException {
        var saved = this.postRepository.save(req.body(Post.class));
        return created(URI.create("/posts/" + saved.getId())).build();
    }

    public ServerResponse get(ServerRequest req) {
        return this.postRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(post -> ok().body(post))
                .orElse(notFound().build());
    }

    public ServerResponse update(ServerRequest req) throws ServletException, IOException {
        var data = req.body(Post.class);

        return postRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        post -> {
                            post.setTitle(data.getTitle());
                            post.setContent(data.getContent());
                            return post;
                        }
                )
                .map(post -> this.postRepository.save(post))
                .map(post -> noContent().build())
                .orElse(notFound().build());
    }

    public ServerResponse delete(ServerRequest req) {
        return this.postRepository.findById(Long.valueOf(req.pathVariable("id")))
                .map(
                        post -> {
                            this.postRepository.delete(post);
                            return noContent().build();
                        }
                )
                .orElse(notFound().build());
    }
}
