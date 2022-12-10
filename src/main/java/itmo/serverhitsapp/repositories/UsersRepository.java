package itmo.serverhitsapp.repositories;

import itmo.serverhitsapp.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
}
