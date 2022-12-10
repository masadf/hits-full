package itmo.serverhitsapp.services;

import io.jsonwebtoken.*;
import itmo.serverhitsapp.exceptions.SessionCountException;
import itmo.serverhitsapp.jwt.JwtUtils;
import itmo.serverhitsapp.jwt.TokenPack;
import itmo.serverhitsapp.jwt.UserJwtInfo;
import itmo.serverhitsapp.jwt.UserTokenClaims;
import itmo.serverhitsapp.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Autowired
    private TokenRepository jwtRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public TokenPack generateAndSaveTokenPack(UserTokenClaims userTokenClaims) {
        if (!isSessionsCountNormal(userTokenClaims.getUsername())) {
            throw new SessionCountException("Сессий для данного пользователя более 5!");
        }
        TokenPack tokenPack = new TokenPack(jwtUtils.generateAccessToken(userTokenClaims), jwtUtils.generateRefreshToken(userTokenClaims));
        UserJwtInfo userJwtInfo = buildUserJwtInfo(userTokenClaims, tokenPack.getRefreshToken());
        jwtRepository.save(userJwtInfo);
        return tokenPack;
    }

    public TokenPack validateAndRefreshRefreshToken(String refreshToken) {
        try {
            UserTokenClaims userTokenClaims = confirmSession(refreshToken);
            return generateAndSaveTokenPack(userTokenClaims);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Невалидный токен!");
        }
    }

    public void removeSessionByRefreshToken(String refreshToken) {
        jwtRepository.deleteByToken(refreshToken);
    }

    private UserJwtInfo buildUserJwtInfo(UserTokenClaims userTokenClaims, String refreshToken) {
        return UserJwtInfo.builder()
                .username(userTokenClaims.getUsername())
                .token(refreshToken)
                .build();
    }

    private UserTokenClaims confirmSession(String refreshToken) {
        UserJwtInfo userJwtInfo;
        try {
            jwtUtils.validateRefreshToken(refreshToken);
            userJwtInfo = getSessionByToken(refreshToken);
        } catch (JwtException e) {
            throw new JwtException("Сессия истекла!");
        } finally {
            removeSessionByRefreshToken(refreshToken);
        }

        return UserTokenClaims.builder()
                .username(userJwtInfo.getUsername())
                .build();
    }

    private UserJwtInfo getSessionByToken(String refreshToken) {
        UserJwtInfo userJwtInfo = jwtRepository.findByToken(refreshToken);
        if (userJwtInfo == null) {
            throw new JwtException("Невалидный токен!");
        }
        return userJwtInfo;
    }

    private boolean isSessionsCountNormal(String username) {
        return jwtRepository.findAllByUsername(username).size() < 5;
    }
}
