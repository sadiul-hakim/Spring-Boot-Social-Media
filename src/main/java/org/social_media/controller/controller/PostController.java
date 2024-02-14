package org.social_media.controller.controller;

import lombok.RequiredArgsConstructor;
import org.social_media.model.Post;
import org.social_media.pojo.PostDTO;
import org.social_media.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/save")
    public ResponseEntity<PostDTO> save(@RequestBody Post post, @RequestParam("userId") long userId){
        PostDTO dto = postService.createPost(post, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> delete(@PathVariable long postId){
        boolean deleted = postService.deletePost(postId);
        return ResponseEntity.ok(Collections.singletonMap("message",STR."Post deleted successfully: \{deleted}"));
    }
    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<?> findByUser(@PathVariable long userId){
        List<PostDTO> postByUserId = postService.findPostByUserId(userId);
        return ResponseEntity.ok(postByUserId);
    }
    @GetMapping("/find/{postId}")
    public ResponseEntity<PostDTO> find(@PathVariable long postId){
        PostDTO postById = postService.findPostById(postId);
        return ResponseEntity.ok(postById);
    }
    @GetMapping("find-all")
    public ResponseEntity<?> findAll(){
        List<PostDTO> all = postService.findAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/like/{postId}/{userId}")
    public ResponseEntity<?> like(@PathVariable long postId,@PathVariable long userId){
        boolean b = postService.likePost(postId, userId);
        return ResponseEntity.ok(Collections.singletonMap("message",STR."User \{userId} liked post \{postId} successfully: \{b}."));
    }
    @GetMapping("/get-likes/{postId}")
    public ResponseEntity<?> allLikes(@PathVariable long postId){
        int likes = postService.allLikes(postId);
        return ResponseEntity.ok(Collections.singletonMap("likes",likes));
    }
}
