package com.server.watermelonserverv1.domain.auth.domain.repository;

import com.server.watermelonserverv1.domain.auth.domain.AuthCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRepository extends CrudRepository<AuthCode, String> {
}
