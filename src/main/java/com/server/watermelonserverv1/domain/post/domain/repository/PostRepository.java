package com.server.watermelonserverv1.domain.post.domain.repository;

import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Page<Post> findByPostType(PostType type, Pageable pageable);
    Optional<Post> findByIdAndPostType(Long id, PostType postType);
    List<Post> findByOrderByDateDesc();
}
