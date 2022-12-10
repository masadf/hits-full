package itmo.serverhitsapp.auth;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserCredentials {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
