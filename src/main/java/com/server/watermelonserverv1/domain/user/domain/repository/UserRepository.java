package com.server.watermelonserverv1.domain.user.domain.repository;

import com.server.watermelonserverv1.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
