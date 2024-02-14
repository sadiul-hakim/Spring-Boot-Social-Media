package org.social_media.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social_media.model.Post;
import org.social_media.model.User;
import org.social_media.pojo.PostDTO;
import org.social_media.repository.PostRepository;
import org.social_media.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostDTO createPost(Post post, long userId) {
        Post savedPost = postRepository.save(post);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new PostDTO();
        }
        user.get().getPosts().add(savedPost);
        return convertToDTO(post);
    }

    public boolean deletePost(long postId) {
        postRepository.deleteById(postId);
        return true;
    }

    public List<PostDTO> findPostByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> postOfUser = postRepository.findByUser(user.get());
        return postOfUser.stream().map(this::convertToDTO).toList();
    }

    public PostDTO findPostById(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) return new PostDTO();
        return convertToDTO(post.get());
    }

    public List<PostDTO> findAll() {
        List<Post> all = postRepository.findAll();
        return all.stream().map(this::convertToDTO).toList();
    }

    @Transactional
    public boolean likePost(long postId, long userId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);
        if (post.isEmpty() || user.isEmpty()) return false;

        if (post.get().getLikes().contains(userId)) {
            post.get().getLikes().remove(userId);
        } else {
            post.get().getLikes().add(userId);
        }

        return true;
    }

    public int allLikes(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.map(value -> value.getLikes().size()).orElse(0);
    }

    public PostDTO convertToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .user(userService.convertToDTO(post.getUser()))
                .likes(post.getLikes())
                .build();
    }
}
