package com.example.testcontainer.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostJdbcRepository postJdbcRepository;

    public PostController(PostJdbcRepository postJdbcRepository) {
        this.postJdbcRepository = postJdbcRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> all() {
        var posts = postJdbcRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id) {
        var post = postJdbcRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> store(@RequestBody Post post, UriComponentsBuilder ucb) {
        var newPost = postJdbcRepository.save(post);
        URI locationOfNewPost = ucb.path("/post/{id}")
                .buildAndExpand(post.id)
                .toUri();
        return ResponseEntity.created(locationOfNewPost).body(newPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
        var existingPost = postJdbcRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        Post updatePost = new Post(existingPost.id, post.title, post.body);
        postJdbcRepository.save(updatePost);

        return ResponseEntity.ok(updatePost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var existingPost = postJdbcRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        postJdbcRepository.delete(existingPost);
        return ResponseEntity.noContent().build();
    }
}
