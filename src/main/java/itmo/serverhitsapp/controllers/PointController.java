package itmo.serverhitsapp.controllers;

import itmo.serverhitsapp.jwt.JwtUtils;
import itmo.serverhitsapp.hits.Hit;
import itmo.serverhitsapp.hits.Point;
import itmo.serverhitsapp.services.HitService;
import jakarta.servlet.http.HttpServletRequest;
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
    private JwtUtils jwtUtils;

    @Autowired
    private HitService hitService;

    @RequestMapping(method = POST, path = "/hit")
    public void addHit(HttpServletRequest httpServletRequest, @RequestBody Point point) {
        hitService.addHit(point, getRequestOwner(httpServletRequest));
    }

    @RequestMapping(method = GET, path = "/hits")
    public List<Hit> getHits(HttpServletRequest httpServletRequest) {
        return hitService.getHits(getRequestOwner(httpServletRequest));
    }

    private String getRequestOwner(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        return jwtUtils.validateAccessToken(token).getUsername();
    }
}
