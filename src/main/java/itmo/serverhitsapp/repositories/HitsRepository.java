package itmo.serverhitsapp.repositories;

import itmo.serverhitsapp.hits.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitsRepository extends JpaRepository<Hit, Long> {
    List<Hit> findByOwner(String owner);
}
