package itmo.serverhitsapp.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserTokenClaims {
    private String username;
    private Role role;
}
