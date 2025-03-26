package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.entity.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {
    Optional<AccessToken> findByToken(String token);
}