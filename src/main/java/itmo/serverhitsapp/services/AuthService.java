package itmo.serverhitsapp.services;

import itmo.serverhitsapp.exceptions.IncorrectUserCredentialsException;
import itmo.serverhitsapp.exceptions.UsernameExistException;

import static itmo.serverhitsapp.jwt.Role.USER;

import itmo.serverhitsapp.jwt.TokenPack;
import itmo.serverhitsapp.jwt.UserTokenClaims;
import itmo.serverhitsapp.model.User;
import itmo.serverhitsapp.model.UserCredentials;
import itmo.serverhitsapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    public TokenPack registration(UserCredentials userCredentials) {
        User user = User.builder()
                .username(userCredentials.getUsername())
                .password(encoder.encode(userCredentials.getPassword()))
                .build();

        if (usersRepository.findById(user.getUsername()).isPresent()) {
            throw new UsernameExistException("Данное имя пользователя занято!");
        }

        usersRepository.save(user);

        return jwtService.generateAndSaveTokenPack(UserTokenClaims.builder()
                .username(user.getUsername())
                .role(USER)
                .build());
    }

    public TokenPack login(UserCredentials userCredentials) {
        User user = usersRepository.findById(userCredentials.getUsername()).orElseThrow(() ->
                new IncorrectUserCredentialsException("Неправильный логин или пароль!"));

        if (!encoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new IncorrectUserCredentialsException("Данного пользователя не существует");
        }

        return jwtService.generateAndSaveTokenPack(UserTokenClaims.builder()
                .username(user.getUsername())
                .role(USER)
                .build());
    }

    public void logout(String refreshToken) {
        jwtService.removeSessionByRefreshToken(refreshToken);
    }

}
