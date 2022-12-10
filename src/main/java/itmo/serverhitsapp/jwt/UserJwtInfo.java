package itmo.serverhitsapp.jwt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tokens")
public class UserJwtInfo {
    @Id
    @GeneratedValue
    private long id;
    @JoinColumn
    private String username;
    private Role role;
    private String token;
}
