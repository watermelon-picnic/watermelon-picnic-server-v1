package com.server.watermelonserverv1.domain.post.domain.repository;

import com.server.watermelonserverv1.domain.post.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
