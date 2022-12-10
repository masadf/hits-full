package itmo.serverhitsapp.services;

import itmo.serverhitsapp.hits.Hit;
import itmo.serverhitsapp.hits.Point;
import itmo.serverhitsapp.hits.PointHandler;
import itmo.serverhitsapp.repositories.HitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HitService {

    @Autowired
    private HitsRepository hitsRepository;

    @Autowired
    private PointHandler pointHandler;

    public void addHit(Point point, String username) {
        Hit hit = pointHandler.calculateHitInfo(point);
        hit.setOwner(username);
        hitsRepository.save(hit);
    }

    public List<Hit> getHits(String username) {
        return hitsRepository.findByOwner(username);
    }
}
