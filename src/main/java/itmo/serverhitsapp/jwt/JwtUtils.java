package itmo.serverhitsapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.access-token.secret}")
    private String accessTokenKey;

    @Value("${jwt.refresh-token.secret}")
    private String refreshTokenKey;

    public UserTokenClaims validateRefreshToken(String refreshToken) {
        Claims jwtClaims;
        try {
            jwtClaims = Jwts.parser()
                    .setSigningKey(refreshTokenKey)
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException("Токен невалиден!");
        }
        return buildUserTokenClaims(jwtClaims);
    }

    public UserTokenClaims validateAccessToken(String accessToken) {
        Claims jwtClaims;
        try {
            jwtClaims = Jwts.parser()
                    .setSigningKey(accessTokenKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Токен невалиден!");
        }

        return buildUserTokenClaims(jwtClaims);
    }

    public String generateAccessToken(UserTokenClaims userTokenClaims) {
        int accessTokenTime = 15 * 60;
        return Jwts.builder()
                .setIssuer("masadf")
                .addClaims(tokenDataToMap(userTokenClaims))
                .setExpiration(Date.from(Instant.now().plusSeconds(accessTokenTime)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(accessTokenKey)
                )
                .compact();
    }

    public String generateRefreshToken(UserTokenClaims userTokenClaims) {
        int refreshTokenTime = 30 * 24 * 60 * 60;
        return Jwts.builder()
                .setIssuer("masadf")
                .addClaims(tokenDataToMap(userTokenClaims))
                .setExpiration(Date.from(Instant.now().plusSeconds(refreshTokenTime)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(refreshTokenKey)
                )
                .compact();
    }

    private UserTokenClaims buildUserTokenClaims(Claims claims) {
        return UserTokenClaims.builder()
                .username(claims.get("username", String.class))
                .role(Role.valueOf(claims.get("role", String.class)))
                .build();
    }

    private Map<String, Object> tokenDataToMap(UserTokenClaims userTokenClaims) {
        return Map.of("username", userTokenClaims.getUsername(),
                "role", userTokenClaims.getRole());
    }
}
