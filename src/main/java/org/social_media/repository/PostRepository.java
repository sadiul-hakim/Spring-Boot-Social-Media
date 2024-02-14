package org.social_media.repository;

import org.social_media.model.Post;
import org.social_media.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
