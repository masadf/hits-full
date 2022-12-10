package itmo.serverhitsapp.repositories;

import itmo.serverhitsapp.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HitsRepository extends JpaRepository<Hit, Long> {
}
