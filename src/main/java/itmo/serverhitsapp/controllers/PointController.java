package itmo.serverhitsapp.controllers;

import itmo.serverhitsapp.repositories.HitsRepository;
import itmo.serverhitsapp.model.Hit;
import itmo.serverhitsapp.model.Point;
import itmo.serverhitsapp.model.PointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PointController {
    @Autowired
    private HitsRepository hitsRepository;

    @Autowired
    private PointHandler pointHandler;

    @RequestMapping(method = POST, path = "/hit")
    public void addHit(@RequestBody Point point) {
        Hit hit = pointHandler.getHitInfo(point);
        hitsRepository.save(hit);
    }

    @RequestMapping(method = GET, path = "/hits")
    public List<Hit> getHits() {
        return hitsRepository.findAll();
    }
}
