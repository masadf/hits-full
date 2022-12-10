package itmo.serverhitsapp.auth;

import itmo.serverhitsapp.jwt.UserJwtInfo;
import itmo.serverhitsapp.hits.Hit;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @OneToMany
    @JoinColumn(name = "username", referencedColumnName = "username")
    private List<UserJwtInfo> sessions;

    @OneToMany
    @JoinColumn(name = "owner", referencedColumnName = "username")
    private List<Hit> hits;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
