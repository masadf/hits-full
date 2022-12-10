package itmo.serverhitsapp.controllers;

import itmo.serverhitsapp.exceptions.NoTokenException;
import itmo.serverhitsapp.jwt.TokenResponse;
import itmo.serverhitsapp.services.AuthService;
import itmo.serverhitsapp.jwt.TokenPack;
import itmo.serverhitsapp.model.UserCredentials;
import itmo.serverhitsapp.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping(method = POST, path = "/registration")
    public TokenResponse registration(@RequestBody UserCredentials userCredentials, HttpServletResponse httpServletResponse) {
        TokenPack tokenPack = authService.registration(userCredentials);
        setRefreshTokenInCookies(httpServletResponse, tokenPack.getRefreshToken());

        return new TokenResponse(tokenPack.getAccessToken());
    }

    @RequestMapping(method = POST, path = "/login")
    public TokenResponse login(@RequestBody UserCredentials userCredentials, HttpServletResponse httpServletResponse) {
        TokenPack tokenPack = authService.login(userCredentials);
        setRefreshTokenInCookies(httpServletResponse, tokenPack.getRefreshToken());

        return new TokenResponse(tokenPack.getAccessToken());
    }

    @RequestMapping(method = POST, path = "/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = getRefreshTokenFromCookies(httpServletRequest);
        removeRefreshTokenFromCookies(httpServletResponse);
        authService.logout(token);
    }

    @RequestMapping(method = POST, path = "/refresh")
    public TokenResponse refresh(HttpServletRequest httpServletRequest) {
        String token = getRefreshTokenFromCookies(httpServletRequest);
        TokenPack tokenPack = jwtService.validateAndRefreshRefreshToken(token);

        return new TokenResponse(tokenPack.getAccessToken());
    }

    private void setRefreshTokenInCookies(HttpServletResponse httpServletResponse, String token) {
        Cookie cookie = new Cookie("bearer", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    private String getRefreshTokenFromCookies(HttpServletRequest httpServletRequest) {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("bearer"))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NoTokenException("Отсутствует токен доступа!");
                }).getValue();
    }

    private void removeRefreshTokenFromCookies(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("bearer", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }
}
