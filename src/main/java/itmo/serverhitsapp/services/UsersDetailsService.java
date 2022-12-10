package itmo.serverhitsapp.services;

import itmo.serverhitsapp.exceptions.IncorrectUserCredentialsException;
import itmo.serverhitsapp.auth.User;
import itmo.serverhitsapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = usersRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new IncorrectUserCredentialsException("Данный пользователь не найден!");
        }

        return usersRepository.findById(username).get();
    }
}
