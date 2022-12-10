package itmo.serverhitsapp.repositories;

import itmo.serverhitsapp.jwt.UserJwtInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<UserJwtInfo, Long> {
    UserJwtInfo findByToken(String Token);

    @Transactional
    void deleteByToken(String Token);

    List<UserJwtInfo> findAllByUsername(String username);
}
