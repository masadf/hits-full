package itmo.serverhitsapp.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPack {
    private String accessToken;
    private String refreshToken;
}
