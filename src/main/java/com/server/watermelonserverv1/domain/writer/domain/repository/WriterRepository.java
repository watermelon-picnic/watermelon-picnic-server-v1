package com.server.watermelonserverv1.domain.writer.domain.repository;

import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WriterRepository extends CrudRepository<Writer, Long> {

    Optional<Writer> findByUser(User user);
}
